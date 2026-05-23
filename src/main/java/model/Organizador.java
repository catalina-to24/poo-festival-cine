package model;

public class Organizador extends Persona {
    private String legajo;

    public Organizador(String nombre, String email, String legajo) {
        super(nombre, email);
        this.legajo = legajo;
    }

    public String getLegajo() {
        return legajo;
    }

    public void setLegajo(String legajo) {
        this.legajo = legajo;
    }

    public void registrarPelicula(Pelicula pelicula, Seccion seccion) {
        if (seccion != null) {
            seccion.agregarPelicula(pelicula);
        }
    }

    public void programarFuncion(Funcion funcion, EdicionFestival edicion) {
        if (edicion != null) {
            edicion.programarFuncion(funcion);
        }
    }
}
