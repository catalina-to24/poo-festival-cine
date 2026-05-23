package ar.edu.uade.festival.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Pelicula implements Serializable {
    private String titulo;
    private String genero;
    private int duracion;
    private Director director;
    private List<Actor> actores;
    private List<Evaluacion> evaluaciones;

    public Pelicula(String titulo, String genero, int duracion, Director director) {
        this.titulo = titulo;
        this.genero = genero;
        this.duracion = duracion;
        this.director = director;
        this.actores = new ArrayList<>();
        this.evaluaciones = new ArrayList<>();
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public Director getDirector() {
        return director;
    }

    public void setDirector(Director director) {
        this.director = director;
    }

    public List<Actor> getActores() {
        return actores;
    }

    public void setActores(List<Actor> actores) {
        this.actores = actores;
    }

    public List<Evaluacion> getEvaluaciones() {
        return evaluaciones;
    }

    public void setEvaluaciones(List<Evaluacion> evaluaciones) {
        this.evaluaciones = evaluaciones;
    }

    public void agregarActor(Actor actor) {
        if (actor != null) {
            actores.add(actor);
        }
    }

    public void agregarEvaluacion(Evaluacion evaluacion) {
        if (evaluacion != null) {
            evaluaciones.add(evaluacion);
        }
    }

    public double calcularPromedio() {
        if (evaluaciones.isEmpty()) {
            return 0;
        }
        double suma = 0;
        for (Evaluacion evaluacion : evaluaciones) {
            suma += evaluacion.getPuntaje();
        }
        return suma / evaluaciones.size();
    }
}
