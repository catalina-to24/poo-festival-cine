package model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class FestivalTest {

    @Test
    public void testPeliculaCalcularPromedio() {
        Director director = new Director("Juan", "juan@example.com", "AR");
        Pelicula p = new Pelicula("Titulo", "Drama", 120, director);
        p.agregarEvaluacion(new Evaluacion(8.0, "Buena", LocalDate.now()));
        p.agregarEvaluacion(new Evaluacion(6.0, "Regular", LocalDate.now()));
        assertEquals(7.0, p.calcularPromedio(), 1e-6);
    }

    @Test
    public void testSeccionObtenerGanadora() {
        Director d = new Director("Ana", "ana@example.com", "AR");
        Pelicula p1 = new Pelicula("Peli1", "Comedia", 90, d);
        Pelicula p2 = new Pelicula("Peli2", "Accion", 100, d);
        p1.agregarEvaluacion(new Evaluacion(5.0, "ok", LocalDate.now()));
        p1.agregarEvaluacion(new Evaluacion(6.0, "ok", LocalDate.now()));
        p2.agregarEvaluacion(new Evaluacion(9.0, "great", LocalDate.now()));

        Seccion s = new Seccion("Competencia", "Seccion competencia");
        s.agregarPelicula(p1);
        s.agregarPelicula(p2);

        Pelicula ganadora = s.obtenerGanadora();
        assertNotNull(ganadora);
        assertEquals("Peli2", ganadora.getTitulo());
    }

    @Test
    public void testEdicionYFestivalBuscarEdicionYDeterminarGanadores() {
        Festival festival = new Festival("MiFestival");
        EdicionFestival ed = new EdicionFestival(2024, "Ciudad", LocalDate.now(), LocalDate.now().plusDays(5));

        Director d = new Director("Diego", "diego@example.com", "AR");
        Pelicula pA = new Pelicula("A", "Doc", 80, d);
        Pelicula pB = new Pelicula("B", "Doc", 85, d);
        pA.agregarEvaluacion(new Evaluacion(4.0, "x", LocalDate.now()));
        pA.agregarEvaluacion(new Evaluacion(5.0, "x", LocalDate.now()));
        pB.agregarEvaluacion(new Evaluacion(7.0, "x", LocalDate.now()));

        Seccion s1 = new Seccion("Seccion1", "Desc");
        s1.agregarPelicula(pA);
        Seccion s2 = new Seccion("Seccion2", "Desc");
        s2.agregarPelicula(pB);

        ed.agregarSeccion(s1);
        ed.agregarSeccion(s2);

        festival.agregarEdicion(ed);

        EdicionFestival buscada = festival.buscarEdicion(2024);
        assertNotNull(buscada);

        var ganadoras = buscada.determinarGanadores();
        assertEquals(2, ganadoras.size());
        assertTrue(ganadoras.stream().anyMatch(p -> p.getTitulo().equals("A")));
        assertTrue(ganadoras.stream().anyMatch(p -> p.getTitulo().equals("B")));
    }
}
