package com.example.navegacao_demo;

import android.location.Location;
import android.os.Handler;
import android.os.Looper;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.Locale;

/**
 * Classe responsavel por calcular a distancia percorrida
 * e exibir na tela do aplicativo.
 */
public class Distance extends Thread{
    private Handler handler;
    private Dados dados;
    private DecrementoThread decrementoThread;
    private boolean isRunning;
    private TextView distanciaPercorrer;
    private TextView tempoRestante;
    private double latitudeInicial;
    private double longitudeInicial;
    private double latitudeFinal;
    private double longitudeFinal;
    private boolean auxiliador = true;

    /**
     * construtor com dois textview, um para imprimir a distancia e outro para a criação da classe decremento.
     * Tambem são passados como parâmetros a classe dados e as coordenadas de destino.
     * @param textView
     * @param textView1
     * @param dados
     * @param latitudeFinal
     * @param longitudeFinal
     */
    public Distance(TextView textView,TextView textView1, Dados dados, double latitudeFinal, double longitudeFinal) {

        handler = new Handler(Looper.getMainLooper());
        isRunning = true;

        this.distanciaPercorrer = textView;
        this.tempoRestante = textView1;
        this.dados = dados;
        this.latitudeFinal = latitudeFinal;
        this.longitudeFinal= longitudeFinal;

    }

    /**
     * Método run que calcula a distancia percorrida.
     */
    @Override
    public void run() {

        while (isRunning) {

            float[] DistanciaReal = new float[1];
            float[] DistanciaTotal = new float[1];

            try {
                Thread.sleep(5000);// de 5 em 5 segundos ele atualiza a distancia percorrida na tela do app.
                /**
                 * este "if" é usado para coletar os dados de latitude e longitude inicial, ele é
                 * executado apenas uma vez durante a navegação. isso permite armazenar a localização inicial
                 * na classe Dados.
                 *
                 * É aqui tambem que inicializamos a classe decremento e damos start nela, isso porque
                 * temos todas as informações necessarias para sua inicialização.
                 */
                if(auxiliador){
                    setLatitudeInicial(dados.getLatitude());
                    setLongitudeInicial(dados.getLongitude());
                    Location.distanceBetween(latitudeInicial,longitudeInicial,dados.getLatitudeFinal(),dados.getLongitudeFinal(),DistanciaTotal);
                    dados.setDistanciaTotal(DistanciaTotal[0]);
                    decrementoThread = new DecrementoThread(dados.tempoTotalInteiro(),tempoRestante);
                    decrementoThread.start();

                }
                /**
                 * nesta parte do código é que atualizamos a distancia real na tela do aplicativo,
                 * e tambem setamos na classe dados.
                 */
                Location.distanceBetween(latitudeInicial,longitudeInicial,dados.getLatitudeFinal(),dados.getLongitudeFinal(),DistanciaTotal);
                auxiliador = false;
                Location.distanceBetween(latitudeInicial,longitudeInicial,dados.getLatitude(),dados.getLongitude(),DistanciaReal);
                dados.setDistanciaTotal(DistanciaTotal[0]);
                dados.setDistancia(DistanciaReal[0]);

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        /**
                         * método run do handler, usado para impressão.
                         */
                        DecimalFormat decimalFormat = new DecimalFormat("#.##");
                        String formattedNumber = decimalFormat.format(DistanciaReal[0]);
                        String conversao = formattedNumber + " Metros" ;
                        distanciaPercorrer.setText(conversao);
                    }
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public void setLatitudeInicial(double latitudeInicial){
       this.latitudeInicial = latitudeInicial;
    }
    public void setLongitudeInicial(double longitudeInicial){
        this.longitudeInicial = longitudeInicial;
    }

    /**
     * método responsavel por parar a thread, e que chama o metodo stop da thread decrementoThread.
     */
    public void stopThread() {
        isRunning = false;
        decrementoThread.stopThread();
    }
}
