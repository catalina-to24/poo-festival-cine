package model;

import java.util.ArrayList;
import java.util.List;

public class Espectador extends Persona {
    private String dni;
    private List<Compra> compras;
    private AbonoFestival abonoFestival;

    public Espectador(String nombre, String email, String dni) {
        super(nombre, email);
        this.dni = dni;
        this.compras = new ArrayList<>();
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public List<Compra> getCompras() {
        return compras;
    }

    public void setCompras(List<Compra> compras) {
        this.compras = compras;
    }

    public AbonoFestival getAbonoFestival() {
        return abonoFestival;
    }

    public void setAbonoFestival(AbonoFestival abonoFestival) {
        this.abonoFestival = abonoFestival;
    }

    public Entrada comprarEntrada(Funcion funcion, Butaca butaca) {
        if (funcion == null) {
            return null;
        }
        return funcion.venderEntrada(this, butaca);
    }

    public void adquirirAbono(AbonoFestival abono) {
        if (abono != null && abono.validarUso()) {
            this.abonoFestival = abono;
        }
    }

    public boolean tieneAbonoVigente() {
        return abonoFestival != null && abonoFestival.validarUso();
    }

    public void agregarCompra(Compra compra) {
        if (compra != null) {
            compras.add(compra);
        }
    }
}
