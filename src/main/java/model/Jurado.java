package model;

public class Jurado extends Persona {
    private String especialidad;

    public Jurado(String nombre, String email, String especialidad) {
        super(nombre, email);
        this.especialidad = especialidad;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public Evaluacion evaluarPelicula(Pelicula pelicula, double puntaje, String comentario) {
        if (pelicula == null) {
            return null;
        }
        Evaluacion evaluacion = new Evaluacion(puntaje, comentario, java.time.LocalDate.now());
        pelicula.agregarEvaluacion(evaluacion);
        return evaluacion;
    }
}
