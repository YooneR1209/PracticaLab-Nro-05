package models;

public class AdyacenciaJSON {
    private int origen;
    private int destino;
    private double peso;

    public AdyacenciaJSON(int origen, int destino, double peso) {
        this.origen = origen;
        this.destino = destino;
        this.peso = peso;
    }

    // Getters y setters (opcional, dependiendo de si los necesitas)
    public int getOrigen() {
        return origen;
    }

    public int getDestino() {
        return destino;
    }

    public double getPeso() {
        return peso;
    }
}