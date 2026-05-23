package ar.edu.uade.festival.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class EstadoSistema implements Serializable {
    private Festival festival;
    private List<Sala> salas;
    private List<Pelicula> peliculas;
    private List<Director> directores;
    private List<Actor> actores;
    private List<Espectador> espectadores;
    private List<Jurado> jurados;

    public EstadoSistema() {
        this.festival = new Festival("Festival de Cine");
        this.salas = new ArrayList<>();
        this.peliculas = new ArrayList<>();
        this.directores = new ArrayList<>();
        this.actores = new ArrayList<>();
        this.espectadores = new ArrayList<>();
        this.jurados = new ArrayList<>();
    }

    public Festival getFestival() {
        return festival;
    }

    public void setFestival(Festival festival) {
        this.festival = festival;
    }

    public List<Sala> getSalas() {
        return salas;
    }

    public void setSalas(List<Sala> salas) {
        this.salas = salas;
    }

    public List<Pelicula> getPeliculas() {
        return peliculas;
    }

    public void setPeliculas(List<Pelicula> peliculas) {
        this.peliculas = peliculas;
    }

    public List<Director> getDirectores() {
        return directores;
    }

    public void setDirectores(List<Director> directores) {
        this.directores = directores;
    }

    public List<Actor> getActores() {
        return actores;
    }

    public void setActores(List<Actor> actores) {
        this.actores = actores;
    }

    public List<Espectador> getEspectadores() {
        return espectadores;
    }

    public void setEspectadores(List<Espectador> espectadores) {
        this.espectadores = espectadores;
    }

    public List<Jurado> getJurados() {
        return jurados;
    }

    public void setJurados(List<Jurado> jurados) {
        this.jurados = jurados;
    }
}