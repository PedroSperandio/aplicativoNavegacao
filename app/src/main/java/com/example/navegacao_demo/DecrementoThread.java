package com.example.navegacao_demo;

import android.widget.TextView;

import java.util.Locale;

/**
 * Classe reponsável por decrementar a contagem do tempo que o veículo deverá fazer
 * o percurso.
 */
public class DecrementoThread extends Thread {
    private int tempoemSegundos;
    private TextView tempoRestante;

    /**
     * Construtor com o textview de impressão do consumo na tela e o tempo em segundos
     * que o veiculo deverá finalizar a rota.
     * @param tempoemSegundos
     * @param textView
     */
    public DecrementoThread(int tempoemSegundos, TextView textView) {
        this.tempoemSegundos = tempoemSegundos;
        this.tempoRestante = textView;
    }

    @Override
    public void run() {
        /**
         * Será executado até que o tempo fique zero, e depois será impresso
         * na tela "tempo esgotado".
         */
        while (tempoemSegundos > 0) {

            int horas = tempoemSegundos / 3600;
            int minutos = (tempoemSegundos % 3600) / 60;
            int segundos = (tempoemSegundos % 60);

            String tempo = String.format(Locale.getDefault(), "%02d:%02d:%02d", horas, minutos, segundos);
            tempoRestante.setText(tempo);

            try {
                Thread.sleep(1000); // Aguarda 1 segundo.
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            tempoemSegundos--;
        }
        tempoRestante.setText("Tempo esgotado!");
    }

    /**
     * Finaliza a thread.
     */
    public void stopThread() {
        tempoemSegundos = 0;
    }
}
