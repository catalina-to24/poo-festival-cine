package ar.edu.uade.festival.service;

import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;

import ar.edu.uade.festival.excepcion.OperacionNoPermitidaException;
import ar.edu.uade.festival.model.AbonoFestival;
import ar.edu.uade.festival.model.Entrada;
import ar.edu.uade.festival.model.Espectador;
import ar.edu.uade.festival.model.EstadoSistema;
import ar.edu.uade.festival.persistencia.EstadoSistemaPersistencia;

class FestivalSistemaTest {
        public static void main(String[] args) {
                FestivalSistemaTest smoke = new FestivalSistemaTest();
                smoke.noDebePermitirFuncionesSuperpuestasEnMismaSala();
                smoke.debeEvitarVentaDuplicadaDeButacaEnMismaFuncion();
                smoke.debePermitirMismaButacaEnFuncionesDistintas();
                smoke.elAbonoDebeAplicarseYConsumirUsos();
                smoke.debeCalcularOcupacionDeFuncionYSala();
                System.out.println("Smoke tests OK");
        }

        private ContextoPrueba nuevoContexto() {
                Path ruta = Path.of("data", "estado-test-" + System.nanoTime() + ".ser");
                EstadoSistemaPersistencia persistencia = new EstadoSistemaPersistencia(ruta);
                EstadoSistema estado = new EstadoSistema();

                FestivalService festivalService = new FestivalService(estado, persistencia);
                PeliculaService peliculaService = new PeliculaService(estado, persistencia);
                VentaService ventaService = new VentaService(estado, persistencia, festivalService);
                return new ContextoPrueba(festivalService, peliculaService, ventaService);
        }

        void noDebePermitirFuncionesSuperpuestasEnMismaSala() {
                ContextoPrueba ctx = nuevoContexto();

                ctx.festivalService.registrarEdicion(2026, "Buenos Aires", LocalDate.of(2026, 9, 1), LocalDate.of(2026, 9, 30));
                ctx.festivalService.registrarSala("Sala 1", 10);
                ctx.peliculaService.registrarPelicula("Pelicula A", "Drama", 120);

                ctx.festivalService.programarFuncion(2026, "Pelicula A", "Sala 1", LocalDateTime.of(2026, 9, 10, 18, 0));

                assertThrowsOperacionNoPermitida(() ->
                                ctx.festivalService.programarFuncion(2026, "Pelicula A", "Sala 1", LocalDateTime.of(2026, 9, 10, 19, 0))
                );
        }

        void debeEvitarVentaDuplicadaDeButacaEnMismaFuncion() {
                ContextoPrueba ctx = nuevoContexto();
                prepararEscenarioBasicoVentas(ctx);

                ctx.ventaService.registrarEspectador("Ana", "ana@mail.com", "111");
                ctx.ventaService.registrarEspectador("Luis", "luis@mail.com", "222");

                Entrada primeraEntrada = ctx.ventaService.venderEntrada(
                                2026, "Pelicula A", "Sala 1", LocalDateTime.of(2026, 9, 10, 18, 0), "111", 1, "A"
                );
                assertNotNull(primeraEntrada, "La primera entrada deberia venderse.");

                assertThrowsOperacionNoPermitida(() ->
                                ctx.ventaService.venderEntrada(2026, "Pelicula A", "Sala 1", LocalDateTime.of(2026, 9, 10, 18, 0), "222", 1, "A")
                );
        }

        void debePermitirMismaButacaEnFuncionesDistintas() {
                ContextoPrueba ctx = nuevoContexto();
                prepararEscenarioBasicoVentas(ctx);

                ctx.ventaService.registrarEspectador("Ana", "ana@mail.com", "111");
                ctx.ventaService.registrarEspectador("Luis", "luis@mail.com", "222");

                Entrada entradaFuncion1 = ctx.ventaService.venderEntrada(
                                2026, "Pelicula A", "Sala 1", LocalDateTime.of(2026, 9, 10, 18, 0), "111", 1, "A"
                );
                Entrada entradaFuncion2 = ctx.ventaService.venderEntrada(
                                2026, "Pelicula A", "Sala 1", LocalDateTime.of(2026, 9, 10, 21, 0), "222", 1, "A"
                );

                assertNotNull(entradaFuncion1, "La entrada de la primera funcion no puede ser nula.");
                assertNotNull(entradaFuncion2, "La entrada de la segunda funcion no puede ser nula.");
        }

        void elAbonoDebeAplicarseYConsumirUsos() {
                ContextoPrueba ctx = nuevoContexto();
                prepararEscenarioBasicoVentas(ctx);

                Espectador espectador = ctx.ventaService.registrarEspectador("Ana", "ana@mail.com", "111");
                AbonoFestival abono = ctx.ventaService.comprarAbono("111", "AB-01", 2, 10);

                assertTrue(abono.isVigente(), "El abono deberia iniciar vigente.");
                assertEquals(2, abono.getCantidadFunciones(), "El abono deberia tener 2 usos.");

                ctx.ventaService.venderEntrada(2026, "Pelicula A", "Sala 1", LocalDateTime.of(2026, 9, 10, 18, 0), "111", 1, "A");
                assertEquals(1, espectador.getAbonoFestival().getCantidadFunciones(), "Luego de la primera compra deberia quedar 1 uso.");
                assertTrue(espectador.getAbonoFestival().isVigente(), "Con 1 uso restante, el abono sigue vigente.");

                ctx.ventaService.venderEntrada(2026, "Pelicula A", "Sala 1", LocalDateTime.of(2026, 9, 10, 21, 0), "111", 2, "A");
                assertEquals(0, espectador.getAbonoFestival().getCantidadFunciones(), "Luego de la segunda compra no deben quedar usos.");
                assertFalse(espectador.getAbonoFestival().isVigente(), "Sin usos, el abono no debe seguir vigente.");
        }

        void debeCalcularOcupacionDeFuncionYSala() {
                ContextoPrueba ctx = nuevoContexto();
                prepararEscenarioBasicoVentas(ctx);

                ctx.ventaService.registrarEspectador("Ana", "ana@mail.com", "111");
                ctx.ventaService.registrarEspectador("Luis", "luis@mail.com", "222");
                ctx.ventaService.registrarEspectador("Juan", "juan@mail.com", "333");

                ctx.ventaService.venderEntrada(2026, "Pelicula A", "Sala 1", LocalDateTime.of(2026, 9, 10, 18, 0), "111", 1, "A");
                ctx.ventaService.venderEntrada(2026, "Pelicula A", "Sala 1", LocalDateTime.of(2026, 9, 10, 18, 0), "222", 2, "A");
                ctx.ventaService.venderEntrada(2026, "Pelicula A", "Sala 1", LocalDateTime.of(2026, 9, 10, 21, 0), "333", 1, "A");

                double ocupacionFuncion = ctx.ventaService.consultarOcupacionFuncion(
                                2026, "Pelicula A", "Sala 1", LocalDateTime.of(2026, 9, 10, 18, 0)
                );
                double ocupacionSala = ctx.ventaService.porcentajeOcupacionSala("Sala 1");

                assertEquals(20.0, ocupacionFuncion, 0.0001, "La ocupacion de funcion deberia ser 20%.");
                assertEquals(15.0, ocupacionSala, 0.0001, "La ocupacion total de sala deberia ser 15%.");
        }

        private void prepararEscenarioBasicoVentas(ContextoPrueba ctx) {
                ctx.festivalService.registrarEdicion(2026, "Buenos Aires", LocalDate.of(2026, 9, 1), LocalDate.of(2026, 9, 30));
                ctx.festivalService.registrarSala("Sala 1", 10);
                ctx.festivalService.registrarButaca("Sala 1", 1, "A");
                ctx.festivalService.registrarButaca("Sala 1", 2, "A");
                ctx.peliculaService.registrarPelicula("Pelicula A", "Drama", 120);

                ctx.festivalService.programarFuncion(2026, "Pelicula A", "Sala 1", LocalDateTime.of(2026, 9, 10, 18, 0));
                ctx.festivalService.programarFuncion(2026, "Pelicula A", "Sala 1", LocalDateTime.of(2026, 9, 10, 21, 0));
        }

        private void assertThrowsOperacionNoPermitida(Runnable accion) {
                try {
                        accion.run();
                        throw new AssertionError("Se esperaba OperacionNoPermitidaException pero no se lanzo.");
                } catch (OperacionNoPermitidaException expected) {
                        // Excepcion esperada.
                }
        }

        private void assertNotNull(Object valor, String mensaje) {
                if (valor == null) {
                        throw new AssertionError(mensaje);
                }
        }

        private void assertTrue(boolean condicion, String mensaje) {
                if (!condicion) {
                        throw new AssertionError(mensaje);
                }
        }

        private void assertFalse(boolean condicion, String mensaje) {
                if (condicion) {
                        throw new AssertionError(mensaje);
                }
        }

        private void assertEquals(int esperado, int actual, String mensaje) {
                if (esperado != actual) {
                        throw new AssertionError(mensaje + " Esperado: " + esperado + ", actual: " + actual);
                }
        }

        private void assertEquals(double esperado, double actual, double tolerancia, String mensaje) {
                if (Math.abs(esperado - actual) > tolerancia) {
                        throw new AssertionError(mensaje + " Esperado: " + esperado + ", actual: " + actual);
                }
        }

        private record ContextoPrueba(FestivalService festivalService, PeliculaService peliculaService, VentaService ventaService) {
        }
}
