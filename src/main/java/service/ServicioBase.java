package service;

import model.EstadoSistema;
import persistencia.EstadoSistemaPersistencia;

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