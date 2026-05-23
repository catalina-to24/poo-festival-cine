package ar.edu.uade.festival.service;

import java.util.ArrayList;
import java.util.List;

import ar.edu.uade.festival.excepcion.DatoInvalidoException;
import ar.edu.uade.festival.excepcion.EntidadNoEncontradaException;
import ar.edu.uade.festival.excepcion.OperacionNoPermitidaException;
import ar.edu.uade.festival.model.Actor;
import ar.edu.uade.festival.model.Director;
import ar.edu.uade.festival.model.EstadoSistema;
import ar.edu.uade.festival.model.Pelicula;
import ar.edu.uade.festival.persistencia.EstadoSistemaPersistencia;

public class PeliculaService extends ServicioBase {
    public PeliculaService(EstadoSistema estado, EstadoSistemaPersistencia persistencia) {
        super(estado, persistencia);
    }

    public Director registrarDirector(String nombre, String email, String nacionalidad) {
        if (nombre == null || nombre.isBlank() || email == null || email.isBlank()) {
            throw new DatoInvalidoException("Datos invalidos para registrar el director.");
        }
        if (buscarDirector(email) != null) {
            throw new OperacionNoPermitidaException("Ya existe un director con ese email.");
        }
        Director director = new Director(nombre, email, nacionalidad);
        estado.getDirectores().add(director);
        guardar();
        return director;
    }

    public Actor registrarActor(String nombre, String email, boolean personajePrincipal) {
        if (nombre == null || nombre.isBlank() || email == null || email.isBlank()) {
            throw new DatoInvalidoException("Datos invalidos para registrar el actor.");
        }
        if (buscarActor(email) != null) {
            throw new OperacionNoPermitidaException("Ya existe un actor con ese email.");
        }
        Actor actor = new Actor(nombre, email, personajePrincipal);
        estado.getActores().add(actor);
        guardar();
        return actor;
    }

    public Pelicula registrarPelicula(String titulo, String genero, int duracion) {
        if (titulo == null || titulo.isBlank() || genero == null || genero.isBlank() || duracion <= 0) {
            throw new DatoInvalidoException("Datos invalidos para registrar la pelicula.");
        }
        if (buscarPelicula(titulo) != null) {
            throw new OperacionNoPermitidaException("Ya existe una pelicula con ese titulo.");
        }
        Pelicula pelicula = new Pelicula(titulo, genero, duracion, null);
        estado.getPeliculas().add(pelicula);
        guardar();
        return pelicula;
    }

    public void asignarDirectorAPelicula(String tituloPelicula, String emailDirector) {
        Pelicula pelicula = buscarPelicula(tituloPelicula);
        if (pelicula == null) {
            throw new EntidadNoEncontradaException("No se encontro la pelicula indicada.");
        }
        Director director = buscarDirector(emailDirector);
        if (director == null) {
            throw new EntidadNoEncontradaException("No se encontro el director indicado.");
        }
        pelicula.setDirector(director);
        guardar();
    }

    public void asociarActorAPelicula(String tituloPelicula, String emailActor) {
        Pelicula pelicula = buscarPelicula(tituloPelicula);
        if (pelicula == null) {
            throw new EntidadNoEncontradaException("No se encontro la pelicula indicada.");
        }
        Actor actor = buscarActor(emailActor);
        if (actor == null) {
            throw new EntidadNoEncontradaException("No se encontro el actor indicado.");
        }
        if (!pelicula.getActores().contains(actor)) {
            pelicula.agregarActor(actor);
            guardar();
        }
    }

    public List<Pelicula> listarPeliculas() {
        return new ArrayList<>(estado.getPeliculas());
    }

    public List<Director> listarDirectores() {
        return new ArrayList<>(estado.getDirectores());
    }

    public List<Actor> listarActores() {
        return new ArrayList<>(estado.getActores());
    }

    public Pelicula buscarPelicula(String titulo) {
        if (titulo == null) {
            return null;
        }
        for (Pelicula pelicula : estado.getPeliculas()) {
            if (pelicula.getTitulo() != null && pelicula.getTitulo().equalsIgnoreCase(titulo)) {
                return pelicula;
            }
        }
        return null;
    }

    public Director buscarDirector(String email) {
        if (email == null) {
            return null;
        }
        for (Director director : estado.getDirectores()) {
            if (director.getEmail() != null && director.getEmail().equalsIgnoreCase(email)) {
                return director;
            }
        }
        return null;
    }

    public Actor buscarActor(String email) {
        if (email == null) {
            return null;
        }
        for (Actor actor : estado.getActores()) {
            if (actor.getEmail() != null && actor.getEmail().equalsIgnoreCase(email)) {
                return actor;
            }
        }
        return null;
    }
}