package com.example.navegacao_demo;

import android.location.Location;
import android.os.Handler;
import android.os.Looper;
import android.widget.TextView;
import java.text.DecimalFormat;

/**
 * Classe responsavel por gerar a velocidade recomendada para o veiculo
 * finalizar o percurso dentro do tempo.
 */
public class EstimatedSpeedThread extends Thread{
    private Handler handler;
    private Dados dados;
    private boolean isRunning;
    private TextView velocidadeEstimada;

    /**
     * construtor com o textview para a impressão na tela e a classe Dados como parâmetros.
     * @param textView
     * @param dados
     */
    public EstimatedSpeedThread(TextView textView, Dados dados) {
        handler = new Handler(Looper.getMainLooper());
        isRunning = true;
        this.velocidadeEstimada = textView;
        this.dados = dados;
    }

    @Override
    public void run() {

        while (isRunning) {
            double velocidade;
            try {
                Thread.sleep(6000); // de 6 em 6 segundos a velocidade recomendada é atualizada.
                /**
                 * é aqui que é implementado a reconciliação de dados, onde conseguimos encontrar
                 * a velocidade que o veiculo precisa estar para chegar no seu destino dentro do tempo.
                 */
                velocidade = ((dados.getDistanciaTotal() - dados.getDistancia())/(dados.tempoTotal()-dados.getTempo()));
                if(velocidade <=4){
                    velocidade = 4.16;
                }
                double finalVelocidade = velocidade;
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        /**
                         * Impressão da velocidade recomendada em Km/h.
                         */
                        DecimalFormat decimalFormat = new DecimalFormat("#.##");
                        String formattedNumber = decimalFormat.format(finalVelocidade *3.6);
                        String conversao = formattedNumber + " Km/h" ;
                        velocidadeEstimada.setText(conversao);
                    }
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void stopThread() {
        isRunning = false;
    }
}
