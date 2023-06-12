package com.example.navegacao_demo;

/**
 * Classe responsavel pelo armazenamento dos dados do aplicativo, com ela conseguimos fazer
 * o envio e o recebimento dos valores. É uma classe facilitadora, permitindo as threads trocar
 * informações.
 */
public class Dados {

    /**
     * Declaração das variaveis que armazenam as informações.
     */
    private double latitude;
    private double longitude;
    private double latitudeFinal;
    private double longitudeFinal;
    private float distancia;
    private float distanciaTotal;
    private int tempo;
    private int tempoDecrementado;
    private float velocidade;

    private double consumoTotal;

    /**
     * Construtor com dois parametros iniciais, latitude e longitude do destino,
     * visto que estas coordenadas são setadas pelo desenvolvedor.
     * @param latitudeFinal
     * @param longitudeFinal
     */
    public Dados(double latitudeFinal, double longitudeFinal){
        this.latitudeFinal = latitudeFinal;
        this.longitudeFinal = longitudeFinal;
    }

    /**
     * Todos os métodos set(sincronizado) das variaveis, são por esses métodos que conseguimos
     * atribuir valores as variaveis.
     */
    public synchronized void setLatitude(double latitude){
        this.latitude  = latitude;
    }

    public synchronized void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public synchronized void setDistancia(float distancia){
        this.distancia = distancia;
    }
    public synchronized void setTempo(int tempo){
        this.tempo = tempo;
    }
    public synchronized void setVelocidade(float velocidade){
        this.velocidade = velocidade;
    }
    public synchronized void setDistanciaTotal(float distanciaTotal){
        this.distanciaTotal = distanciaTotal;
    }
    public synchronized void setConsumoTotal(double consumoTotal){
        this.consumoTotal = consumoTotal;
    }

    /**
     * Todos os métodos get(sincronizado) das variaveis, são por esses métodos que conseguimos
     * visualizar os valores as variaveis.
     */
    public synchronized double getLatitudeFinal(){
        return latitudeFinal;
    }
    public synchronized double getLongitudeFinal(){
        return longitudeFinal;
    }

    public synchronized double getLatitude(){
        return latitude;
    }

    public synchronized double getLongitude() {
        return longitude;
    }
    public synchronized float getDistancia(){
        return distancia;
    }
    public synchronized int getTempo(){
        return tempo;
    }

    public synchronized float getVelocidade() {
        return velocidade;
    }
    public synchronized float getDistanciaTotal() {
        return distanciaTotal;
    }

    public synchronized double getConsumoTotal(){
        return consumoTotal;
    }

    /**
     * O método tempo total retorna o valor do tempo total do percurso,
     * levando em consideração a velocidade média esperada de 4.16 M/s,
     * ou seja, 15 km/h.
     * @return
     */
    public synchronized double tempoTotal(){
        return distanciaTotal/4.16;
    }

    /**
     * O método tempoTotalInteiro retorna a aproximação do tempo do método acima,
     * porem com o valor inteiro. Em um ponto do código será necessário o valor do tempo
     * do tipo inteiro.
     * @return
     */
    public synchronized int tempoTotalInteiro(){
        return (int) (distanciaTotal/4.16);
    }

}
