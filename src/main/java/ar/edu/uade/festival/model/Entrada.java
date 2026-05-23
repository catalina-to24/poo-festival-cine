package ar.edu.uade.festival.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Entrada implements Serializable {
    private String codigo;
    private double precio;
    private LocalDateTime fechaCompra;
    private Funcion funcion;
    private Butaca butaca;
    private Espectador espectador;

    public Entrada(String codigo, double precio, LocalDateTime fechaCompra, Funcion funcion, Butaca butaca, Espectador espectador) {
        this.codigo = codigo;
        this.precio = precio;
        this.fechaCompra = fechaCompra;
        this.funcion = funcion;
        this.butaca = butaca;
        this.espectador = espectador;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public LocalDateTime getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(LocalDateTime fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    public Funcion getFuncion() {
        return funcion;
    }

    public void setFuncion(Funcion funcion) {
        this.funcion = funcion;
    }

    public Butaca getButaca() {
        return butaca;
    }

    public void setButaca(Butaca butaca) {
        this.butaca = butaca;
    }

    public Espectador getEspectador() {
        return espectador;
    }

    public void setEspectador(Espectador espectador) {
        this.espectador = espectador;
    }

    public void emitir() {
        if (funcion != null && funcion.getPelicula() != null && funcion.getSala() != null && butaca != null) {
            System.out.println("Entrada emitida: " + codigo + " | Pelicula: " + funcion.getPelicula().getTitulo() + " | Sala: " + funcion.getSala().getNombre() + " | Butaca: " + butaca.getFila() + "-" + butaca.getNumero());
        }
    }
}
