package model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Compra implements Serializable {
    private LocalDateTime fecha;
    private List<Entrada> entradas;
    private double total;
    private AbonoFestival abonoFestival;
    private boolean confirmada;

    public Compra() {
        this.fecha = LocalDateTime.now();
        this.entradas = new ArrayList<>();
        this.total = 0;
        this.confirmada = false;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public List<Entrada> getEntradas() {
        return entradas;
    }

    public void setEntradas(List<Entrada> entradas) {
        this.entradas = entradas;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public AbonoFestival getAbonoFestival() {
        return abonoFestival;
    }

    public void setAbonoFestival(AbonoFestival abonoFestival) {
        this.abonoFestival = abonoFestival;
    }

    public boolean isConfirmada() {
        return confirmada;
    }

    public void setConfirmada(boolean confirmada) {
        this.confirmada = confirmada;
    }

    public void agregarEntrada(Entrada entrada) {
        if (entrada != null) {
            entradas.add(entrada);
        }
    }

    public double calcularTotal() {
        double calculado = 0;
        for (Entrada entrada : entradas) {
            calculado += entrada.getPrecio();
        }
        if (abonoFestival != null && abonoFestival.validarUso()) {
            calculado -= abonoFestival.calcularDescuento(calculado);
        }
        total = calculado;
        return total;
    }

    public void confirmarCompra() {
        if (confirmada) {
            return;
        }
        calcularTotal();
        if (abonoFestival != null && abonoFestival.validarUso()) {
            abonoFestival.usarFuncion();
        }
        confirmada = true;
    }

    public void aplicarAbono(AbonoFestival abono) {
        if (abono != null && abono.validarUso()) {
            this.abonoFestival = abono;
        }
    }
}
