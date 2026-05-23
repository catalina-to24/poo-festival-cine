package service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import excepcion.DatoInvalidoException;
import excepcion.EntidadNoEncontradaException;
import excepcion.OperacionNoPermitidaException;
import model.AbonoFestival;
import model.Butaca;
import model.Compra;
import model.EdicionFestival;
import model.Entrada;
import model.Espectador;
import model.EstadoSistema;
import model.Funcion;
import model.Sala;
import persistencia.EstadoSistemaPersistencia;

public class VentaService extends ServicioBase {
    private final FestivalService festivalService;

    public VentaService(EstadoSistema estado, EstadoSistemaPersistencia persistencia, FestivalService festivalService) {
        super(estado, persistencia);
        this.festivalService = festivalService;
    }

    public Espectador registrarEspectador(String nombre, String email, String dni) {
        if (nombre == null || nombre.isBlank() || email == null || email.isBlank() || dni == null || dni.isBlank()) {
            throw new DatoInvalidoException("Datos invalidos para registrar el espectador.");
        }
        if (buscarEspectador(dni) != null) {
            throw new OperacionNoPermitidaException("Ya existe un espectador con ese DNI.");
        }
        Espectador espectador = new Espectador(nombre, email, dni);
        estado.getEspectadores().add(espectador);
        guardar();
        return espectador;
    }

    public AbonoFestival comprarAbono(String dniEspectador, String codigo, int cantidadFunciones, double descuento) {
        Espectador espectador = buscarEspectador(dniEspectador);
        if (espectador == null) {
            throw new EntidadNoEncontradaException("No se encontro el espectador indicado.");
        }
        if (codigo == null || codigo.isBlank() || cantidadFunciones <= 0 || descuento < 0) {
            throw new DatoInvalidoException("Datos invalidos para el abono.");
        }
        AbonoFestival abonoFestival = new AbonoFestival(codigo, cantidadFunciones, descuento, true);
        espectador.adquirirAbono(abonoFestival);
        guardar();
        return abonoFestival;
    }

    public Entrada venderEntrada(int anioEdicion, String tituloPelicula, String nombreSala, LocalDateTime fechaHora, String dniEspectador, int numeroButaca, String fila) {
        Espectador espectador = buscarEspectador(dniEspectador);
        if (espectador == null) {
            throw new EntidadNoEncontradaException("No se encontro el espectador indicado.");
        }
        Funcion funcion = festivalService.buscarFuncion(anioEdicion, tituloPelicula, nombreSala, fechaHora);
        if (funcion == null) {
            throw new EntidadNoEncontradaException("No se encontro la funcion indicada.");
        }
        Sala sala = festivalService.buscarSala(nombreSala);
        if (sala == null) {
            throw new EntidadNoEncontradaException("No se encontro la sala indicada.");
        }
        Butaca butaca = buscarButaca(sala, numeroButaca, fila);
        if (butaca == null) {
            throw new EntidadNoEncontradaException("No se encontro la butaca indicada.");
        }
        Entrada entrada = espectador.comprarEntrada(funcion, butaca);
        if (entrada == null) {
            throw new OperacionNoPermitidaException("No fue posible vender la entrada.");
        }
        Compra compra = new Compra();
        compra.agregarEntrada(entrada);
        if (espectador.tieneAbonoVigente()) {
            compra.aplicarAbono(espectador.getAbonoFestival());
        }
        compra.confirmarCompra();
        espectador.agregarCompra(compra);
        guardar();
        return entrada;
    }

    public double consultarOcupacionFuncion(int anioEdicion, String tituloPelicula, String nombreSala, LocalDateTime fechaHora) {
        Funcion funcion = festivalService.buscarFuncion(anioEdicion, tituloPelicula, nombreSala, fechaHora);
        if (funcion == null) {
            throw new EntidadNoEncontradaException("No se encontro la funcion indicada.");
        }
        return funcion.calcularOcupacion();
    }

    public double porcentajeOcupacionSala(String nombreSala) {
        Sala sala = festivalService.buscarSala(nombreSala);
        if (sala == null) {
            throw new EntidadNoEncontradaException("No se encontro la sala indicada.");
        }
        if (sala.getCapacidad() == 0) {
            return 0;
        }

        int totalEntradas = 0;
        int totalCapacidadTeorica = 0;

        for (EdicionFestival edicion : estado.getFestival().getEdiciones()) {
            for (Funcion funcion : edicion.getFunciones()) {
                if (funcion.getSala() != null && funcion.getSala().getNombre().equalsIgnoreCase(nombreSala)) {
                    totalEntradas += funcion.getEntradasVendidas().size();
                    totalCapacidadTeorica += sala.getCapacidad();
                }
            }
        }

        if (totalCapacidadTeorica == 0) {
            return 0;
        }

        return totalEntradas * 100.0 / totalCapacidadTeorica;
    }

    public List<Espectador> listarEspectadores() {
        return new ArrayList<>(estado.getEspectadores());
    }

    public Espectador buscarEspectador(String dni) {
        if (dni == null) {
            return null;
        }
        for (Espectador espectador : estado.getEspectadores()) {
            if (espectador.getDni() != null && espectador.getDni().equalsIgnoreCase(dni)) {
                return espectador;
            }
        }
        return null;
    }

    private Butaca buscarButaca(Sala sala, int numero, String fila) {
        if (sala == null || fila == null) {
            return null;
        }
        for (Butaca butaca : sala.getButacas()) {
            if (butaca.getNumero() == numero && butaca.getFila() != null && butaca.getFila().equalsIgnoreCase(fila)) {
                return butaca;
            }
        }
        return null;
    }
}