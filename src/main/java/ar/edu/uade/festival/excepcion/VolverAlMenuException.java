package ar.edu.uade.festival.excepcion;

public class VolverAlMenuException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public VolverAlMenuException() {
        super();
    }

    public VolverAlMenuException(String message) {
        super(message);
    }
}
