package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EdicionFestival implements Serializable {
    private int anio;
    private String ciudad;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private List<Seccion> secciones;
    private List<Funcion> funciones;
    private List<Premio> premios;

    public EdicionFestival(int anio, String ciudad, LocalDate fechaInicio, LocalDate fechaFin) {
        this.anio = anio;
        this.ciudad = ciudad;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.secciones = new ArrayList<>();
        this.funciones = new ArrayList<>();
        this.premios = new ArrayList<>();
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public List<Seccion> getSecciones() {
        return secciones;
    }

    public void setSecciones(List<Seccion> secciones) {
        this.secciones = secciones;
    }

    public List<Funcion> getFunciones() {
        return funciones;
    }

    public void setFunciones(List<Funcion> funciones) {
        this.funciones = funciones;
    }

    public List<Premio> getPremios() {
        return premios;
    }

    public void setPremios(List<Premio> premios) {
        this.premios = premios;
    }

    public void agregarSeccion(Seccion seccion) {
        if (seccion != null) {
            secciones.add(seccion);
        }
    }

    public void programarFuncion(Funcion funcion) {
        if (funcion != null) {
            funciones.add(funcion);
        }
    }

    public void agregarPremio(Premio premio) {
        if (premio != null) {
            premios.add(premio);
        }
    }

    public List<Pelicula> determinarGanadores() {
        List<Pelicula> ganadoras = new ArrayList<>();
        for (Seccion seccion : secciones) {
            Pelicula ganadora = seccion.obtenerGanadora();
            if (ganadora != null) {
                ganadoras.add(ganadora);
            }
        }
        return ganadoras;
    }
}
