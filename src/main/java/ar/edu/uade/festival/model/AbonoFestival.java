package ar.edu.uade.festival.model;

import java.io.Serializable;

public class AbonoFestival implements Serializable {
    private String codigo;
    private int cantidadFunciones;
    private double descuento;
    private boolean vigente;

    public AbonoFestival(String codigo, int cantidadFunciones, double descuento, boolean vigente) {
        this.codigo = codigo;
        this.cantidadFunciones = cantidadFunciones;
        this.descuento = descuento;
        this.vigente = vigente;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public int getCantidadFunciones() {
        return cantidadFunciones;
    }

    public void setCantidadFunciones(int cantidadFunciones) {
        this.cantidadFunciones = cantidadFunciones;
    }

    public double getDescuento() {
        return descuento;
    }

    public void setDescuento(double descuento) {
        this.descuento = descuento;
    }

    public boolean isVigente() {
        return vigente;
    }

    public void setVigente(boolean vigente) {
        this.vigente = vigente;
    }

    public double calcularDescuento(double total) {
        return total * (descuento / 100.0);
    }

    public boolean validarUso() {
        return vigente && cantidadFunciones > 0;
    }

    public void usarFuncion() {
        if (validarUso()) {
            cantidadFunciones--;
            if (cantidadFunciones == 0) {
                vigente = false;
            }
        }
    }
}
