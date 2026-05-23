package ar.edu.uade.festival.model;

import java.io.Serializable;
import java.time.LocalDate;

public class Evaluacion implements Serializable {
    private double puntaje;
    private String comentario;
    private LocalDate fecha;

    public Evaluacion(double puntaje, String comentario, LocalDate fecha) {
        this.puntaje = normalizarPuntaje(puntaje);
        this.comentario = comentario;
        this.fecha = fecha;
    }

    private double normalizarPuntaje(double valor) {
        if (valor < 1) {
            return 1;
        }
        if (valor > 10) {
            return 10;
        }
        return valor;
    }

    public double getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(double puntaje) {
        this.puntaje = normalizarPuntaje(puntaje);
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
}
