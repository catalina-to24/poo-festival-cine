package excepcion;

public class DatoInvalidoException extends RuntimeException {
    public DatoInvalidoException(String mensaje) {
        super(mensaje);
    }
}