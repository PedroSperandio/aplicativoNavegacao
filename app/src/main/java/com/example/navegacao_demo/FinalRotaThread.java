package com.example.navegacao_demo;

import android.widget.TextView;

import java.util.Locale;

/**
 * Classe responsavel pelo final da navegação.
 */
public class FinalRotaThread extends Thread {
    /**
     * todas as threads que serão pausadas no final da navegação
     * foi inicializada, e tambem, todos os textView para a impressão da mensagem na tela do app.
     */
    private Distance distance;
    private VelocidadeThread velocidadeThread;
    private EstimatedSpeedThread estimatedSpeedThread;
    private IncrementThread incrementThread;
    private ConsumoThread consumoThread;
    private TextView tempoRestante;
    private TextView distanciaPercorrer;
    private TextView velocidadeAtual;
    private TextView velocidadeEstimada;
    private TextView tempoDeslocamento;
    private TextView ConsumoSaida;
    private Dados dados;
    private boolean isRunning;

    /**
     * Construtor com todos os parametros necessários.
     * @param dados
     * @param textView
     * @param textView1
     * @param textView2
     * @param textView3
     * @param textView4
     * @param textView5
     * @param distance
     * @param velocidadeThread
     * @param estimatedSpeedThread
     * @param incrementThread
     * @param consumoThread
     */
    public FinalRotaThread(Dados dados, TextView textView, TextView textView1,
                           TextView textView2, TextView textView3,
                           TextView textView4, TextView textView5, Distance distance,
                           VelocidadeThread velocidadeThread,EstimatedSpeedThread estimatedSpeedThread,
                           IncrementThread incrementThread, ConsumoThread consumoThread)
                           {
        this.tempoRestante = textView;
        this.distanciaPercorrer = textView1;
        this.velocidadeAtual = textView2;
        this.velocidadeEstimada = textView3;
        this.tempoDeslocamento = textView4;
        this.ConsumoSaida = textView5;
        this.distance = distance;
        this.velocidadeThread = velocidadeThread;
        this.estimatedSpeedThread = estimatedSpeedThread;
        this.incrementThread = incrementThread;
        this.consumoThread = consumoThread;


        isRunning = true;
        this.dados = dados;
    }

    /**
     * Essa thread ficará rodando de 1 em 1 segundo, testando se as coordenadas iniciais são iguais as finais.
     */
    @Override
    public void run() {
        while (isRunning) {
            if(dados.getLatitudeFinal()==dados.getLatitude() && dados.getLongitudeFinal()==dados.getLongitude()){
                /**
                 * se forem iguais será impresso na tela "Rota finalizada" em todos os campus.
                 * tambem chama o método endroute, que para as threads.
                 */
                tempoRestante.setText("Rota finalizada");
                distanciaPercorrer.setText("Rota finalizada");
                velocidadeAtual.setText("Rota finalizada");
                velocidadeEstimada.setText("Rota finalizada");
                tempoDeslocamento.setText("Rota finalizada");
                endroute();
            }

            try {
                Thread.sleep(1000); // Atualização de 1 em 1 segundo.
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Método que para algumas threads com a finalização da rota.
     */
    public void endroute(){
        distance.stopThread();
        velocidadeThread.stopThread();
        estimatedSpeedThread.stopThread();
        incrementThread.stopThread();
        consumoThread.stopThread();
    }
    public void stopThread() {
        isRunning = false;
    }
}
