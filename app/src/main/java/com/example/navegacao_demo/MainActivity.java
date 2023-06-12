package com.example.navegacao_demo;

import static android.service.controls.ControlsProviderService.TAG;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

/**
 * A classe MainActivity é a classe que é destinada a iniciar as atividades e o aplicativo.
 */
public class MainActivity extends AppCompatActivity {
    /**
     * Criação das variaveis necessarias.
     */
    private IncrementThread incrementThread;
    private LocationThread locationThread;
    private Distance distance;
    private VelocidadeThread velocidadeThread;
    private EstimatedSpeedThread estimatedSpeedThread;
    private ConsumoThread consumoThread;

    private FinalRotaThread finalRotaThread;

    private Dados dados;
    private TextView tempoDeslocamento;
    private TextView localizacaoAtual;
    private TextView Destino;
    private TextView distanciaPercorrer;
    private TextView velocidadeAtual;
    private TextView velocidadeEstimada;
    private TextView ConsumoSaida;

    private TextView tempoRestante;
    private double latitudeFinal;
    private double longitudeFinal;


    /**
     * Método onCreate inicializa o aplicativo.
     *
     */


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         * Coordernadas do destino.
         */
        latitudeFinal = -21.2326;
        longitudeFinal = -44.9955;

        /**
         * conexão da variavel textView com o id que foi colocado na criação do layout.
         */
        localizacaoAtual = findViewById(R.id.localizacaoAtual);
        tempoDeslocamento = findViewById(R.id.tempoDeslocamento);
        distanciaPercorrer = findViewById(R.id.distanciaPercorrer);
        velocidadeAtual = findViewById(R.id.velocidadeAtual);
        velocidadeEstimada = findViewById(R.id.velocidadeEstimada);
        ConsumoSaida = findViewById(R.id.ConsumoSaida);
        Destino = findViewById(R.id.Destino);
        tempoRestante = findViewById(R.id.tempoRestante);

        /**
         * Inicialização de todas as classes.
         */
        dados = new Dados(latitudeFinal, longitudeFinal);
        locationThread = new LocationThread(this,localizacaoAtual, dados);
        incrementThread = new IncrementThread(tempoDeslocamento,dados);
        distance = new Distance(distanciaPercorrer,tempoRestante, dados,latitudeFinal,longitudeFinal);
        velocidadeThread = new VelocidadeThread(velocidadeAtual,dados);
        estimatedSpeedThread = new EstimatedSpeedThread(velocidadeEstimada,dados);
        consumoThread = new ConsumoThread(ConsumoSaida,dados);
        finalRotaThread = new FinalRotaThread(dados,tempoRestante,distanciaPercorrer,velocidadeAtual,
                velocidadeEstimada,tempoDeslocamento,ConsumoSaida,distance,velocidadeThread,estimatedSpeedThread,
                incrementThread,consumoThread);


        /**
         * inicialização da thread que rotorna a localização.
         */

        locationThread.start();
    }

    /**
     * método que é executado apos o click no botão "iniciar navegação".
     * Sendo assim, serão iniciadas algumas threads.
     * @param v
     */
    public void iniciarNav(View v){
        incrementThread.start();
        destinoCoordenadas();
        distance.start();
        velocidadeThread.start();
        estimatedSpeedThread.start();
        consumoThread.start();
        finalRotaThread.start();
    }
    /**
     * método que é executado apos o click no botão "Finalizar navegação".
     * Sendo assim, serão pausadas algumas threads.
     * @param v
     */
    public void reset(View v){
        incrementThread.stopThread();
        distance.stopThread();
        velocidadeThread.stopThread();
        estimatedSpeedThread.stopThread();
        consumoThread.stopThread();
    }
    /**
     * método que é executado apos o click no botão "Sair do aplicativo".
     * Com isso, o aplicativo será finalizado(fechado).
     * @param v
     */
    public void sairApp(View v){
        finish();
        System.exit(0);
    }

    /**
     * Método para a impressão das coordernadas de destino na tela.
     */
    public void destinoCoordenadas(){
        String localizacao = "Latitude: " + latitudeFinal + "\nLongitude: " + longitudeFinal;
        Destino.setText(localizacao);
    }

    /**
     *Método que envia a permissão para a busca da localização do seu celular e tambem usar o gps
     * do dispositivo.
     *
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == LocationThread.REQUEST_LOCATION_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permissão de localização concedida, inicia as atualizações de localização
                locationThread.startLocationUpdates();
            } else {
                // Permissão de localização negada, lida com essa situação
                Log.e(TAG, "Permissão de localização negada.");
            }
        }
    }

    /**
     * Método que pausa todas as threads do sistema.
     */
    @Override
    protected void onDestroy() {

        super.onDestroy();
        incrementThread.stopThread();
        distance.stopThread();
        velocidadeThread.stopThread();
        estimatedSpeedThread.stopThread();
        consumoThread.stopThread();
        finalRotaThread.stopThread();
        if (locationThread != null) {
            locationThread.stopThread();
        }

    }
}
