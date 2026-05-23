package service;

import java.util.ArrayList;
import java.util.List;

import excepcion.DatoInvalidoException;
import excepcion.EntidadNoEncontradaException;
import excepcion.OperacionNoPermitidaException;
import model.EdicionFestival;
import model.EstadoSistema;
import model.Evaluacion;
import model.Jurado;
import model.Pelicula;
import persistencia.EstadoSistemaPersistencia;

public class EvaluacionService extends ServicioBase {
    private final FestivalService festivalService;

    public EvaluacionService(EstadoSistema estado, EstadoSistemaPersistencia persistencia, FestivalService festivalService) {
        super(estado, persistencia);
        this.festivalService = festivalService;
    }

    public Jurado registrarJurado(String nombre, String email, String especialidad) {
        if (nombre == null || nombre.isBlank() || email == null || email.isBlank()) {
            throw new DatoInvalidoException("Datos invalidos para registrar el jurado.");
        }
        if (buscarJurado(email) != null) {
            throw new OperacionNoPermitidaException("Ya existe un jurado con ese email.");
        }
        Jurado jurado = new Jurado(nombre, email, especialidad);
        estado.getJurados().add(jurado);
        guardar();
        return jurado;
    }

    public Evaluacion registrarEvaluacion(String emailJurado, String tituloPelicula, double puntaje, String comentario) {
        Jurado jurado = buscarJurado(emailJurado);
        if (jurado == null) {
            throw new EntidadNoEncontradaException("No se encontro el jurado indicado.");
        }
        Pelicula pelicula = buscarPelicula(tituloPelicula);
        if (pelicula == null) {
            throw new EntidadNoEncontradaException("No se encontro la pelicula indicada.");
        }
        Evaluacion evaluacion = jurado.evaluarPelicula(pelicula, puntaje, comentario);
        guardar();
        return evaluacion;
    }

    public double calcularPuntajePromedio(String tituloPelicula) {
        Pelicula pelicula = buscarPelicula(tituloPelicula);
        if (pelicula == null) {
            throw new EntidadNoEncontradaException("No se encontro la pelicula indicada.");
        }
        return pelicula.calcularPromedio();
    }

    public List<Pelicula> determinarGanadoras(int anioEdicion) {
        EdicionFestival edicion = festivalService.buscarEdicion(anioEdicion);
        if (edicion == null) {
            throw new EntidadNoEncontradaException("No se encontro la edicion indicada.");
        }
        return new ArrayList<>(edicion.determinarGanadores());
    }

    public List<Jurado> listarJurados() {
        return new ArrayList<>(estado.getJurados());
    }

    public Jurado buscarJurado(String email) {
        if (email == null) {
            return null;
        }
        for (Jurado jurado : estado.getJurados()) {
            if (jurado.getEmail() != null && jurado.getEmail().equalsIgnoreCase(email)) {
                return jurado;
            }
        }
        return null;
    }

    private Pelicula buscarPelicula(String titulo) {
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
}