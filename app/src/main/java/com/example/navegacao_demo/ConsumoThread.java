package com.example.navegacao_demo;

import android.os.Handler;
import android.os.Looper;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.Locale;

/**
 * Classe responsavel por calcular e atualizar o consumo do veiculo no aplicativo.
 */
public class ConsumoThread extends Thread {
    private Handler handler;
    private Dados dados;
    private boolean isRunning;
    private TextView ConsumoSaida;

    /**
     * Construtor com o textview de impressão do consumo na tela e a classe Dados.
     * @param textView
     * @param dados
     */
    public ConsumoThread(TextView textView, Dados dados) {
        handler = new Handler(Looper.getMainLooper());
        isRunning = true;
        this.dados = dados;
        this.ConsumoSaida= textView;
    }

    @Override
    public void run() {
        while (isRunning) {
            try {
                /**
                 * Aguarda o tempo do percurso inteiro dividido por 15.
                 * o percurso está sendo dividido em 15 partes.
                 */
                Thread.sleep((dados.tempoTotalInteiro()/15)*1000);
                double consumoVeiculo = 0.0;
                /**
                 * Calculo do consumo do veiculo de acordo com a velocidade em que ele estiver.
                 */
                if(dados.getVelocidade()<(20/3.6)){
                    consumoVeiculo = (12*1000);
                }
                else if(dados.getVelocidade()<(40/3.6)){
                    consumoVeiculo = (10*1000);
                }
                else if(dados.getVelocidade()<(60/3.6)){
                    consumoVeiculo = (8*1000);
                }
                else if(dados.getVelocidade()<(80/3.6)){
                    consumoVeiculo = (6*1000);
                }
                else{
                    consumoVeiculo = (4*1000);
                }
                double consumoTotal = ((dados.getVelocidade()*(dados.tempoTotalInteiro()/15))/consumoVeiculo);
                consumoTotal = consumoTotal+ dados.getConsumoTotal();
                dados.setConsumoTotal(consumoTotal);


                double finalConsumoTotal = consumoTotal;
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        /**
                         * impressão do consumo do veiculo na tela do app.
                         */
                        DecimalFormat decimalFormat = new DecimalFormat("#.##");
                        String formattedNumber = decimalFormat.format(finalConsumoTotal *3.6);
                        String conversao = formattedNumber + " Litros" ;
                        ConsumoSaida.setText(conversao);
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
