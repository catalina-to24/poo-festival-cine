package ar.edu.uade.festival.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Funcion implements Serializable {
    private LocalDateTime fechaHora;
    private String estado;
    private Pelicula pelicula;
    private Sala sala;
    private List<Entrada> entradasVendidas;

    public Funcion(LocalDateTime fechaHora, String estado, Pelicula pelicula, Sala sala) {
        this.fechaHora = fechaHora;
        this.estado = estado;
        this.pelicula = pelicula;
        this.sala = sala;
        this.entradasVendidas = new ArrayList<>();
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Pelicula getPelicula() {
        return pelicula;
    }

    public void setPelicula(Pelicula pelicula) {
        this.pelicula = pelicula;
    }

    public Sala getSala() {
        return sala;
    }

    public void setSala(Sala sala) {
        this.sala = sala;
    }

    public List<Entrada> getEntradasVendidas() {
        return entradasVendidas;
    }

    public void setEntradasVendidas(List<Entrada> entradasVendidas) {
        this.entradasVendidas = entradasVendidas;
    }

    public boolean validarDisponibilidad(Butaca butaca) {
        if (butaca == null) {
            return false;
        }
        for (Entrada entrada : entradasVendidas) {
            Butaca butacaVendida = entrada.getButaca();
            if (butacaVendida != null
                    && butacaVendida.getNumero() == butaca.getNumero()
                    && butacaVendida.getFila() != null
                    && butacaVendida.getFila().equalsIgnoreCase(butaca.getFila())) {
                return false;
            }
        }
        return true;
    }

    public Entrada venderEntrada(Espectador espectador, Butaca butaca) {
        if (espectador == null || butaca == null) {
            return null;
        }
        if (!validarDisponibilidad(butaca)) {
            System.out.println("La butaca no esta disponible.");
            return null;
        }
        Entrada entrada = new Entrada(
                "ENT-" + (entradasVendidas.size() + 1),
                1000.0,
                LocalDateTime.now(),
                this,
                butaca,
                espectador
        );
        entradasVendidas.add(entrada);
        return entrada;
    }

    public double calcularOcupacion() {
        if (sala == null || sala.getCapacidad() == 0) {
            return 0;
        }
        return (entradasVendidas.size() * 100.0) / sala.getCapacidad();
    }
}
