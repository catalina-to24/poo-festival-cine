package persistencia;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import model.EstadoSistema;

public class EstadoSistemaPersistencia {
    private final Path rutaArchivo;

    public EstadoSistemaPersistencia() {
        this(Paths.get("data", "estado-festival.ser"));
    }

    public EstadoSistemaPersistencia(Path rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
    }

    public EstadoSistema cargar() {
        if (!Files.exists(rutaArchivo)) {
            return new EstadoSistema();
        }
        try (ObjectInputStream entrada = new ObjectInputStream(Files.newInputStream(rutaArchivo))) {
            Object objeto = entrada.readObject();
            if (objeto instanceof EstadoSistema estadoSistema) {
                return estadoSistema;
            }
            return new EstadoSistema();
        } catch (IOException | ClassNotFoundException ex) {
            throw new IllegalStateException("No se pudo cargar el estado del sistema.", ex);
        }
    }

    public void guardar(EstadoSistema estadoSistema) {
        try {
            Path directorio = rutaArchivo.getParent();
            if (directorio != null) {
                Files.createDirectories(directorio);
            }
            try (ObjectOutputStream salida = new ObjectOutputStream(Files.newOutputStream(rutaArchivo))) {
                salida.writeObject(estadoSistema);
            }
        } catch (IOException ex) {
            throw new IllegalStateException("No se pudo guardar el estado del sistema.", ex);
        }
    }
}