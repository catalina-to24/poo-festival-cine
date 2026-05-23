package ar.edu.uade.festival.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Sala implements Serializable {
    private String nombre;
    private int capacidad;
    private List<Butaca> butacas;

    public Sala(String nombre, int capacidad) {
        this.nombre = nombre;
        this.capacidad = capacidad;
        this.butacas = new ArrayList<>();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public List<Butaca> getButacas() {
        return butacas;
    }

    public void setButacas(List<Butaca> butacas) {
        this.butacas = butacas;
    }

    public void agregarButaca(Butaca butaca) {
        if (butaca != null && butacas.size() < capacidad) {
            butacas.add(butaca);
        }
    }

    public List<Butaca> obtenerButacasDisponibles() {
        List<Butaca> disponibles = new ArrayList<>();
        for (Butaca butaca : butacas) {
            if (butaca.isDisponible()) {
                disponibles.add(butaca);
            }
        }
        return disponibles;
    }
}
