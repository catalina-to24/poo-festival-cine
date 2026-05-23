package ar.edu.uade.festival.service;

import ar.edu.uade.festival.model.EstadoSistema;
import ar.edu.uade.festival.persistencia.EstadoSistemaPersistencia;

abstract class ServicioBase {
    protected final EstadoSistema estado;
    protected final EstadoSistemaPersistencia persistencia;

    protected ServicioBase(EstadoSistema estado, EstadoSistemaPersistencia persistencia) {
        this.estado = estado;
        this.persistencia = persistencia;
    }

    protected void guardar() {
        persistencia.guardar(estado);
    }
}