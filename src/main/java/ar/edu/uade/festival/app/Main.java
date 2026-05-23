package ar.edu.uade.festival.app;

import ar.edu.uade.festival.model.EstadoSistema;
import ar.edu.uade.festival.persistencia.EstadoSistemaPersistencia;
import ar.edu.uade.festival.service.EvaluacionService;
import ar.edu.uade.festival.service.FestivalService;
import ar.edu.uade.festival.service.PeliculaService;
import ar.edu.uade.festival.service.VentaService;
import ar.edu.uade.festival.ui.ConsolaFestivalUI;

public class Main {
    public static void main(String[] args) {
        EstadoSistemaPersistencia persistencia = new EstadoSistemaPersistencia();
        EstadoSistema estado = persistencia.cargar();

        FestivalService festivalService = new FestivalService(estado, persistencia);
        PeliculaService peliculaService = new PeliculaService(estado, persistencia);
        VentaService ventaService = new VentaService(estado, persistencia, festivalService);
        EvaluacionService evaluacionService = new EvaluacionService(estado, persistencia, festivalService);

        ConsolaFestivalUI ui = new ConsolaFestivalUI(festivalService, peliculaService, ventaService, evaluacionService);
        ui.ejecutar();
    }
}