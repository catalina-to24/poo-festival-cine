package ar.edu.uade.festival.model;

import java.io.Serializable;

public class Butaca implements Serializable {
    private int numero;
    private String fila;
    private boolean disponible;

    public Butaca(int numero, String fila, boolean disponible) {
        this.numero = numero;
        this.fila = fila;
        this.disponible = disponible;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getFila() {
        return fila;
    }

    public void setFila(String fila) {
        this.fila = fila;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public void reservar() {
        if (disponible) {
            disponible = false;
        }
    }

    public void liberar() {
        disponible = true;
    }
}
