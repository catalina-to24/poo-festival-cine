package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Seccion implements Serializable {
    private String nombre;
    private String descripcion;
    private List<Pelicula> peliculas;

    public Seccion(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.peliculas = new ArrayList<>();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<Pelicula> getPeliculas() {
        return peliculas;
    }

    public void setPeliculas(List<Pelicula> peliculas) {
        this.peliculas = peliculas;
    }

    public void agregarPelicula(Pelicula pelicula) {
        if (pelicula != null) {
            peliculas.add(pelicula);
        }
    }

    public Pelicula obtenerGanadora() {
        if (peliculas.isEmpty()) {
            return null;
        }
        Pelicula ganadora = null;
        double mejorPromedio = -1;
        for (Pelicula pelicula : peliculas) {
            double promedio = pelicula.calcularPromedio();
            if (promedio > mejorPromedio) {
                mejorPromedio = promedio;
                ganadora = pelicula;
            }
        }
        return ganadora;
    }
}
