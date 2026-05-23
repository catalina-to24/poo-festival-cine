package ar.edu.uade.festival.model;

public class Director extends Persona {
    private String nacionalidad;

    public Director(String nombre, String email, String nacionalidad) {
        super(nombre, email);
        this.nacionalidad = nacionalidad;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }
}
