package ar.edu.uade.festival.model;

import java.io.Serializable;

public class Premio implements Serializable {
    private String categoria;
    private String descripcion;
    private Pelicula peliculaGanadora;

    public Premio(String categoria, String descripcion) {
        this.categoria = categoria;
        this.descripcion = descripcion;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Pelicula getPeliculaGanadora() {
        return peliculaGanadora;
    }

    public void setPeliculaGanadora(Pelicula peliculaGanadora) {
        this.peliculaGanadora = peliculaGanadora;
    }

    public void asignarGanadora(Pelicula pelicula) {
        if (pelicula != null) {
            this.peliculaGanadora = pelicula;
        }
    }
}
