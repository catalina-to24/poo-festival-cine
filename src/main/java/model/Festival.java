package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Festival implements Serializable {
    private String nombre;
    private List<EdicionFestival> ediciones;

    public Festival(String nombre) {
        this.nombre = nombre;
        this.ediciones = new ArrayList<>();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<EdicionFestival> getEdiciones() {
        return ediciones;
    }

    public void setEdiciones(List<EdicionFestival> ediciones) {
        this.ediciones = ediciones;
    }

    public void agregarEdicion(EdicionFestival edicion) {
        if (edicion != null) {
            ediciones.add(edicion);
        }
    }

    public EdicionFestival buscarEdicion(int anio) {
        for (EdicionFestival edicion : ediciones) {
            if (edicion.getAnio() == anio) {
                return edicion;
            }
        }
        return null;
    }
}
