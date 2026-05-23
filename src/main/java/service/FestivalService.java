package service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import excepcion.DatoInvalidoException;
import excepcion.EntidadNoEncontradaException;
import excepcion.OperacionNoPermitidaException;
import model.Butaca;
import model.EdicionFestival;
import model.EstadoSistema;
import model.Festival;
import model.Funcion;
import model.Pelicula;
import model.Sala;
import model.Seccion;
import persistencia.EstadoSistemaPersistencia;

public class FestivalService extends ServicioBase {
    public FestivalService(EstadoSistema estado, EstadoSistemaPersistencia persistencia) {
        super(estado, persistencia);
    }

    public EdicionFestival registrarEdicion(int anio, String ciudad, LocalDate fechaInicio, LocalDate fechaFin) {
        if (anio <= 0 || ciudad == null || ciudad.isBlank() || fechaInicio == null || fechaFin == null) {
            throw new DatoInvalidoException("Datos invalidos para registrar la edicion.");
        }
        if (fechaFin.isBefore(fechaInicio)) {
            throw new DatoInvalidoException("La fecha de fin no puede ser anterior a la fecha de inicio.");
        }
        if (buscarEdicion(anio) != null) {
            throw new OperacionNoPermitidaException("Ya existe una edicion para el anio indicado.");
        }
        EdicionFestival edicion = new EdicionFestival(anio, ciudad, fechaInicio, fechaFin);
        estado.getFestival().agregarEdicion(edicion);
        guardar();
        return edicion;
    }

    public Seccion crearSeccion(int anioEdicion, String nombre, String descripcion) {
        EdicionFestival edicion = buscarEdicion(anioEdicion);
        if (edicion == null) {
            throw new EntidadNoEncontradaException("No se encontro la edicion indicada.");
        }
        if (nombre == null || nombre.isBlank()) {
            throw new DatoInvalidoException("El nombre de la seccion es obligatorio.");
        }
        if (buscarSeccion(edicion, nombre) != null) {
            throw new OperacionNoPermitidaException("La seccion ya existe en la edicion.");
        }
        Seccion seccion = new Seccion(nombre, descripcion);
        edicion.agregarSeccion(seccion);
        guardar();
        return seccion;
    }

    public Sala registrarSala(String nombre, int capacidad) {
        if (nombre == null || nombre.isBlank() || capacidad <= 0) {
            throw new DatoInvalidoException("Datos invalidos para registrar la sala.");
        }
        if (buscarSala(nombre) != null) {
            throw new OperacionNoPermitidaException("Ya existe una sala con ese nombre.");
        }
        Sala sala = new Sala(nombre, capacidad);
        estado.getSalas().add(sala);
        guardar();
        return sala;
    }

    public Butaca registrarButaca(String nombreSala, int numero, String fila) {
        Sala sala = buscarSala(nombreSala);
        if (sala == null) {
            throw new EntidadNoEncontradaException("No se encontro la sala indicada.");
        }
        if (numero <= 0 || fila == null || fila.isBlank()) {
            throw new DatoInvalidoException("Datos invalidos para registrar la butaca.");
        }
        if (sala.getButacas().size() >= sala.getCapacidad()) {
            throw new OperacionNoPermitidaException("La sala ya alcanzo su capacidad.");
        }
        for (Butaca butaca : sala.getButacas()) {
            if (butaca.getNumero() == numero && butaca.getFila().equalsIgnoreCase(fila)) {
                throw new OperacionNoPermitidaException("La butaca ya existe en la sala.");
            }
        }
        Butaca butaca = new Butaca(numero, fila, true);
        sala.agregarButaca(butaca);
        guardar();
        return butaca;
    }

    public void asociarPeliculaASeccion(int anioEdicion, String nombreSeccion, String tituloPelicula) {
        EdicionFestival edicion = buscarEdicion(anioEdicion);
        if (edicion == null) {
            throw new EntidadNoEncontradaException("No se encontro la edicion indicada.");
        }
        Seccion seccion = buscarSeccion(edicion, nombreSeccion);
        if (seccion == null) {
            throw new EntidadNoEncontradaException("No se encontro la seccion indicada.");
        }
        Pelicula pelicula = buscarPelicula(tituloPelicula);
        if (pelicula == null) {
            throw new EntidadNoEncontradaException("No se encontro la pelicula indicada.");
        }
        if (!seccion.getPeliculas().contains(pelicula)) {
            seccion.agregarPelicula(pelicula);
            guardar();
        }
    }

    public Funcion programarFuncion(int anioEdicion, String tituloPelicula, String nombreSala, LocalDateTime fechaHora) {
        EdicionFestival edicion = buscarEdicion(anioEdicion);
        if (edicion == null) {
            throw new EntidadNoEncontradaException("No se encontro la edicion indicada.");
        }
        Pelicula pelicula = buscarPelicula(tituloPelicula);
        if (pelicula == null) {
            throw new EntidadNoEncontradaException("No se encontro la pelicula indicada.");
        }
        Sala sala = buscarSala(nombreSala);
        if (sala == null) {
            throw new EntidadNoEncontradaException("No se encontro la sala indicada.");
        }
        if (fechaHora == null) {
            throw new DatoInvalidoException("La fecha y hora de la funcion son obligatorias.");
        }
        LocalDateTime finNueva = fechaHora.plusMinutes(pelicula.getDuracion());
        for (Funcion funcion : edicion.getFunciones()) {
            if (funcion.getSala() != null && funcion.getSala().getNombre().equalsIgnoreCase(nombreSala)) {
                LocalDateTime inicioExistente = funcion.getFechaHora();
                LocalDateTime finExistente = inicioExistente.plusMinutes(funcion.getPelicula().getDuracion());
                boolean superpuesta = fechaHora.isBefore(finExistente) && finNueva.isAfter(inicioExistente);
                if (superpuesta) {
                    throw new OperacionNoPermitidaException("La funcion se superpone con otra ya programada en la misma sala.");
                }
            }
        }
        Funcion funcion = new Funcion(fechaHora, "PROGRAMADA", pelicula, sala);
        edicion.programarFuncion(funcion);
        guardar();
        return funcion;
    }

    public List<EdicionFestival> listarEdiciones() {
        return new ArrayList<>(estado.getFestival().getEdiciones());
    }

    public List<Sala> listarSalas() {
        return new ArrayList<>(estado.getSalas());
    }

    public List<Funcion> listarFunciones(int anioEdicion) {
        EdicionFestival edicion = buscarEdicion(anioEdicion);
        if (edicion == null) {
            throw new EntidadNoEncontradaException("No se encontro la edicion indicada.");
        }
        return new ArrayList<>(edicion.getFunciones());
    }

    public EdicionFestival buscarEdicion(int anio) {
        Festival festival = estado.getFestival();
        return festival.buscarEdicion(anio);
    }

    public Sala buscarSala(String nombre) {
        if (nombre == null) {
            return null;
        }
        for (Sala sala : estado.getSalas()) {
            if (sala.getNombre() != null && sala.getNombre().equalsIgnoreCase(nombre)) {
                return sala;
            }
        }
        return null;
    }

    public Funcion buscarFuncion(int anioEdicion, String tituloPelicula, String nombreSala, LocalDateTime fechaHora) {
        EdicionFestival edicion = buscarEdicion(anioEdicion);
        if (edicion == null) {
            return null;
        }
        for (Funcion funcion : edicion.getFunciones()) {
            boolean coincidePelicula = funcion.getPelicula() != null && funcion.getPelicula().getTitulo().equalsIgnoreCase(tituloPelicula);
            boolean coincideSala = funcion.getSala() != null && funcion.getSala().getNombre().equalsIgnoreCase(nombreSala);
            boolean coincideFecha = fechaHora != null && fechaHora.equals(funcion.getFechaHora());
            if (coincidePelicula && coincideSala && coincideFecha) {
                return funcion;
            }
        }
        return null;
    }

    private Seccion buscarSeccion(EdicionFestival edicion, String nombreSeccion) {
        if (edicion == null || nombreSeccion == null) {
            return null;
        }
        for (Seccion seccion : edicion.getSecciones()) {
            if (seccion.getNombre() != null && seccion.getNombre().equalsIgnoreCase(nombreSeccion)) {
                return seccion;
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