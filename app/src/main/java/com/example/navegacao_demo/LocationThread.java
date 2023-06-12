package com.example.navegacao_demo;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

/**
 * Classe thread reponsavel por fornecer a localização atual do celular, dados coletados do gps em tempo real.
 */
public class LocationThread extends Thread {
    public static final int REQUEST_LOCATION_PERMISSION = 1;
    private static final String TAG = "LocationThread";
    private boolean isRunning;
    private Context context;
    /**
     * A classe Handler é usada principalmente em conjunto com a classe Thread para permitir a
     * execução de tarefas assíncronas e a atualização da interface do usuário.
     * Quando uma thread secundária (como uma thread de execução em segundo plano) precisa
     * interagir com a interface do usuário ou executar uma ação na thread principal,
     *  pode usar um Handler para enviar uma mensagem ou um objeto Runnable para a thread principal.
     */
    private Handler handler;
    private Dados dados;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private TextView localizacaoAtual;
    private double latitudeResultado;
    private double longitudeResultado;


    /**
     * Construtor da classe LocationThread, onde é passado como parametro
     * o textview que irá exibir o valor da localização atual, o context que é
     * relacionado a permissão para a localização e a classe dados.
     * @param context
     * @param textView
     * @param dados
     */
    public LocationThread(Context context, TextView textView, Dados dados) {
        this.context = context;
        this.localizacaoAtual = textView;
        this.dados = dados;
        handler = new Handler(Looper.getMainLooper());
        isRunning = true;

    }

    @Override
    public void run() {

        while (isRunning) {

            try {
                Thread.sleep(100); // Aguarda 0.1 segundo para cada execução.

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        // Verifica a permissão de acesso à localização
                        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                                && ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                            // Solicita as permissões em tempo de execução
                            ActivityCompat.requestPermissions((AppCompatActivity) context,
                                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                                    REQUEST_LOCATION_PERMISSION);
                        } else {
                            startLocationUpdates();

                        }
                    }
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public void startLocationUpdates() {
        // Inicializa o gerenciador de localização
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                // Obtém a latitude atual.
                latitudeResultado = location.getLatitude();
                //Envia o valor para a classe dados.
                dados.setLatitude(latitudeResultado);
                // Obtém a longitude atual.
                longitudeResultado = location.getLongitude();
                //Envia o valor para a classe dados.
                dados.setLongitude(longitudeResultado);
                // Imprime os valores da latitude e longitude
                String localizacao = "Latitude: " + latitudeResultado + "\nLongitude: " + longitudeResultado;
                localizacaoAtual.setText(localizacao);

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            @Override
            public void onProviderEnabled(String provider) {
            }

            @Override
            public void onProviderDisabled(String provider) {
            }
        };

        /** Registra o ouvinte de localização */
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
    }
    /** método para parar a thread */
    public void stopThread() {
        // Para a thread e remove o ouvinte de localização
        if (locationManager != null && locationListener != null) {
            locationManager.removeUpdates(locationListener);
        }
        isRunning = false;
    }

}

