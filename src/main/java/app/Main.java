package app;

import model.EstadoSistema;
import persistencia.EstadoSistemaPersistencia;
import service.EvaluacionService;
import service.FestivalService;
import service.PeliculaService;
import service.VentaService;
import ui.ConsolaFestivalUI;

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