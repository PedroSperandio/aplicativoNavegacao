package com.example.navegacao_demo;

import android.os.Handler;
import android.os.Looper;
import android.widget.TextView;
import java.util.Locale;

/**
 * Classe reponsavel por gerar o tempo de deslocamento da navegação e imprimir na tela.
 */
public class IncrementThread extends Thread {
    private Handler handler;
    private Dados dados;
    private boolean isRunning;
    private TextView tempoDeslocamento;

    /**
     * Construtor com o textview de impressão do tempo de deslocamento na tela e a classe Dados.
     * @param textView
     * @param dados
     */
    public IncrementThread(TextView textView, Dados dados) {
        handler = new Handler(Looper.getMainLooper());
        isRunning = true;
        this.dados = dados;
        this.tempoDeslocamento = textView;

    }

    @Override
    public void run() {
        int totalSeconds = 0;

        while (isRunning) {
            try {
                Thread.sleep(1000); // Atualiza de 1 em 1 segundo.
                totalSeconds++;

                /**
                 * conversão de segundos para horas e minutos.
                 */
                final int hours = totalSeconds / 3600;
                final int minutes = (totalSeconds % 3600) / 60;
                final int seconds = totalSeconds % 60;

                /**
                 * envvio da atualização dos segundos para a classe dados.
                 */
                dados.setTempo(totalSeconds);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        /**
                         * Impressão do tempo de deslocamento na tela do app.
                         */
                        String time = String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, seconds);
                        tempoDeslocamento.setText(time);
                    }
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Método para parar a thread.
     */
    public void stopThread() {
        isRunning = false;
    }
}