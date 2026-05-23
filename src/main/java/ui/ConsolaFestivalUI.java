package ui;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

import excepcion.VolverAlMenuException;
import model.AbonoFestival;
import model.Actor;
import model.Director;
import model.EdicionFestival;
import model.Entrada;
import model.Espectador;
import model.Funcion;
import model.Jurado;
import model.Pelicula;
import model.Sala;
import model.Seccion;
import service.EvaluacionService;
import service.FestivalService;
import service.PeliculaService;
import service.VentaService;

public class ConsolaFestivalUI {
    private static final DateTimeFormatter FORMATO_FECHA_HORA = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private final Scanner scanner;
    private final FestivalService festivalService;
    private final PeliculaService peliculaService;
    private final VentaService ventaService;
    private final EvaluacionService evaluacionService;

    public ConsolaFestivalUI(FestivalService festivalService, PeliculaService peliculaService, VentaService ventaService, EvaluacionService evaluacionService) {
        this.scanner = new Scanner(System.in);
        this.festivalService = festivalService;
        this.peliculaService = peliculaService;
        this.ventaService = ventaService;
        this.evaluacionService = evaluacionService;
    }

    public void ejecutar() {
        boolean salir = false;
        while (!salir) {
            mostrarMenu();
            String opcion = leerTexto("Seleccione una opcion: ");
            try {
                switch (opcion) {
                    case "1" -> registrarEdicion();
                    case "2" -> crearSeccion();
                    case "3" -> registrarSala();
                    case "4" -> registrarButaca();
                    case "5" -> registrarPelicula();
                    case "6" -> registrarDirector();
                    case "7" -> registrarActor();
                    case "8" -> asignarDirectorAPelicula();
                    case "9" -> asociarActorAPelicula();
                    case "10" -> asociarPeliculaASeccion();
                    case "11" -> programarFuncion();
                    case "12" -> listarFunciones();
                    case "13" -> registrarEspectador();
                    case "14" -> venderEntrada();
                    case "15" -> comprarAbono();
                    case "16" -> consultarOcupacionFuncion();
                    case "17" -> porcentajeOcupacionSala();
                    case "18" -> registrarJurado();
                    case "19" -> registrarEvaluacion();
                    case "20" -> consultarPuntajePromedio();
                    case "21" -> determinarGanadores();
                    case "0" -> salir = true;
                    default -> System.out.println("Opcion invalida.");
                }
            } catch (VolverAlMenuException ex) {
                // Usuario solicitó volver al menu: simplemente continuar al siguiente ciclo
            } catch (RuntimeException ex) {
                System.out.println(ex.getMessage());
            }
            if (!salir) {
                System.out.println();
            }
        }
    }

    private void mostrarMenu() {
        System.out.println("===== SISTEMA DE GESTION FESTIVAL DE CINE =====");
        System.out.println("1. Registrar edicion");
        System.out.println("2. Crear seccion");
        System.out.println("3. Registrar sala");
        System.out.println("4. Registrar butaca");
        System.out.println("5. Registrar pelicula");
        System.out.println("6. Registrar director");
        System.out.println("7. Registrar actor");
        System.out.println("8. Asignar director a pelicula");
        System.out.println("9. Asociar actor a pelicula");
        System.out.println("10. Asociar pelicula a seccion");
        System.out.println("11. Programar funcion");
        System.out.println("12. Consultar funciones programadas");
        System.out.println("13. Registrar espectador");
        System.out.println("14. Vender entrada");
        System.out.println("15. Comprar abono");
        System.out.println("16. Consultar ocupacion de funcion");
        System.out.println("17. Obtener porcentaje de ocupacion de sala");
        System.out.println("18. Registrar jurado");
        System.out.println("19. Registrar evaluacion");
        System.out.println("20. Consultar puntaje promedio");
        System.out.println("21. Determinar ganadores");
        System.out.println("0. Salir");
    }

    private void asignarDirectorAPelicula() {
        String pelicula = leerTexto("Titulo de la pelicula: ");
        String emailDirector = leerTexto("Email del director: ");
        peliculaService.asignarDirectorAPelicula(pelicula, emailDirector);
        System.out.println("Director asignado a la pelicula.");
    }

    private void asociarActorAPelicula() {
        String pelicula = leerTexto("Titulo de la pelicula: ");
        String emailActor = leerTexto("Email del actor: ");
        peliculaService.asociarActorAPelicula(pelicula, emailActor);
        System.out.println("Actor asociado a la pelicula.");
    }

    private void registrarEdicion() {
        int anio = leerEntero("Anio de la edicion: ");
        String ciudad = leerTexto("Ciudad: ");
        LocalDate inicio = leerFecha("Fecha de inicio (yyyy-MM-dd): ");
        LocalDate fin = leerFecha("Fecha de fin (yyyy-MM-dd): ");
        EdicionFestival edicion = festivalService.registrarEdicion(anio, ciudad, inicio, fin);
        System.out.println("Edicion registrada: " + edicion.getAnio());
    }

    private void crearSeccion() {
        int anio = leerEntero("Anio de la edicion: ");
        String nombre = leerTexto("Nombre de la seccion: ");
        String descripcion = leerTexto("Descripcion: ");
        Seccion seccion = festivalService.crearSeccion(anio, nombre, descripcion);
        System.out.println("Seccion creada: " + seccion.getNombre());
    }

    private void registrarSala() {
        String nombre = leerTexto("Nombre de la sala: ");
        int capacidad = leerEntero("Capacidad: ");
        Sala sala = festivalService.registrarSala(nombre, capacidad);
        System.out.println("Sala registrada: " + sala.getNombre());
    }

    private void registrarButaca() {
        String sala = leerTexto("Nombre de la sala: ");
        int numero = leerEntero("Numero de butaca: ");
        String fila = leerTexto("Fila: ");
        festivalService.registrarButaca(sala, numero, fila);
        System.out.println("Butaca registrada.");
    }

    private void registrarPelicula() {
        String titulo = leerTexto("Titulo: ");
        String genero = leerTexto("Genero: ");
        int duracion = leerEntero("Duracion en minutos: ");
        Pelicula pelicula = peliculaService.registrarPelicula(titulo, genero, duracion);
        System.out.println("Pelicula registrada: " + pelicula.getTitulo());
    }

    private void registrarDirector() {
        String nombre = leerTexto("Nombre: ");
        String email = leerTexto("Email: ");
        String nacionalidad = leerTexto("Nacionalidad: ");
        Director director = peliculaService.registrarDirector(nombre, email, nacionalidad);
        System.out.println("Director registrado: " + director.getNombre());
    }

    private void registrarActor() {
        String nombre = leerTexto("Nombre: ");
        String email = leerTexto("Email: ");
        boolean principal = leerBooleano("Personaje principal? (si/no): ");
        Actor actor = peliculaService.registrarActor(nombre, email, principal);
        System.out.println("Actor registrado: " + actor.getNombre());
    }

    private void asociarPeliculaASeccion() {
        int anio = leerEntero("Anio de la edicion: ");
        String seccion = leerTexto("Nombre de la seccion: ");
        String pelicula = leerTexto("Titulo de la pelicula: ");
        festivalService.asociarPeliculaASeccion(anio, seccion, pelicula);
        System.out.println("Pelicula asociada a la seccion.");
    }

    private void programarFuncion() {
        int anio = leerEntero("Anio de la edicion: ");
        String pelicula = leerTexto("Titulo de la pelicula: ");
        String sala = leerTexto("Nombre de la sala: ");
        LocalDateTime fechaHora = leerFechaHora("Fecha y hora (yyyy-MM-dd HH:mm): ");
        Funcion funcion = festivalService.programarFuncion(anio, pelicula, sala, fechaHora);
        System.out.println("Funcion programada: " + funcion.getPelicula().getTitulo() + " en " + funcion.getSala().getNombre());
    }

    private void listarFunciones() {
        int anio = leerEntero("Anio de la edicion: ");
        List<Funcion> funciones = festivalService.listarFunciones(anio);
        if (funciones.isEmpty()) {
            System.out.println("No hay funciones programadas.");
            return;
        }
        for (Funcion funcion : funciones) {
            System.out.println(funcion.getFechaHora() + " | " + funcion.getPelicula().getTitulo() + " | " + funcion.getSala().getNombre());
        }
    }

    private void registrarEspectador() {
        String nombre = leerTexto("Nombre: ");
        String email = leerTexto("Email: ");
        String dni = leerTexto("DNI: ");
        Espectador espectador = ventaService.registrarEspectador(nombre, email, dni);
        System.out.println("Espectador registrado: " + espectador.getNombre());
    }

    private void venderEntrada() {
        int anio = leerEntero("Anio de la edicion: ");
        String pelicula = leerTexto("Titulo de la pelicula: ");
        String sala = leerTexto("Nombre de la sala: ");
        LocalDateTime fechaHora = leerFechaHora("Fecha y hora de la funcion (yyyy-MM-dd HH:mm): ");
        String dni = leerTexto("DNI del espectador: ");
        int numeroButaca = leerEntero("Numero de butaca: ");
        String fila = leerTexto("Fila: ");
        Entrada entrada = ventaService.venderEntrada(anio, pelicula, sala, fechaHora, dni, numeroButaca, fila);
        System.out.println("Entrada vendida: " + entrada.getCodigo());
    }

    private void comprarAbono() {
        String dni = leerTexto("DNI del espectador: ");
        String codigo = leerTexto("Codigo del abono: ");
        int cantidad = leerEntero("Cantidad de funciones: ");
        double descuento = leerDecimal("Descuento porcentual: ");
        AbonoFestival abono = ventaService.comprarAbono(dni, codigo, cantidad, descuento);
        System.out.println("Abono asignado: " + abono.getCodigo());
    }

    private void consultarOcupacionFuncion() {
        int anio = leerEntero("Anio de la edicion: ");
        String pelicula = leerTexto("Titulo de la pelicula: ");
        String sala = leerTexto("Nombre de la sala: ");
        LocalDateTime fechaHora = leerFechaHora("Fecha y hora de la funcion (yyyy-MM-dd HH:mm): ");
        double ocupacion = ventaService.consultarOcupacionFuncion(anio, pelicula, sala, fechaHora);
        System.out.println("Ocupacion de la funcion: " + String.format("%.2f", ocupacion) + "%");
    }

    private void porcentajeOcupacionSala() {
        String sala = leerTexto("Nombre de la sala: ");
        double ocupacion = ventaService.porcentajeOcupacionSala(sala);
        System.out.println("Porcentaje de ocupacion: " + String.format("%.2f", ocupacion) + "%");
    }

    private void registrarJurado() {
        String nombre = leerTexto("Nombre: ");
        String email = leerTexto("Email: ");
        String especialidad = leerTexto("Especialidad: ");
        Jurado jurado = evaluacionService.registrarJurado(nombre, email, especialidad);
        System.out.println("Jurado registrado: " + jurado.getNombre());
    }

    private void registrarEvaluacion() {
        String emailJurado = leerTexto("Email del jurado: ");
        String pelicula = leerTexto("Titulo de la pelicula: ");
        double puntaje = leerDecimal("Puntaje: ");
        String comentario = leerTexto("Comentario: ");
        evaluacionService.registrarEvaluacion(emailJurado, pelicula, puntaje, comentario);
        System.out.println("Evaluacion registrada.");
    }

    private void consultarPuntajePromedio() {
        String pelicula = leerTexto("Titulo de la pelicula: ");
        double promedio = evaluacionService.calcularPuntajePromedio(pelicula);
        System.out.println("Puntaje promedio: " + String.format("%.2f", promedio));
    }

    private void determinarGanadores() {
        int anio = leerEntero("Anio de la edicion: ");
        List<Pelicula> ganadoras = evaluacionService.determinarGanadoras(anio);
        if (ganadoras.isEmpty()) {
            System.out.println("No hay ganadoras registradas.");
            return;
        }
        for (Pelicula pelicula : ganadoras) {
            System.out.println("Ganadora: " + pelicula.getTitulo() + " - Promedio: " + String.format("%.2f", pelicula.calcularPromedio()));
        }
    }

    private String leerTexto(String mensaje) {
        System.out.print(mensaje + " (m = menu): ");
        String texto = scanner.nextLine().trim();
        if (texto.equalsIgnoreCase("m") || texto.equalsIgnoreCase("menu")) {
            throw new VolverAlMenuException();
        }
        return texto;
    }

    private int leerEntero(String mensaje) {
        while (true) {
            String texto = leerTexto(mensaje);
            try {
                return Integer.parseInt(texto);
            } catch (NumberFormatException ex) {
                System.out.println("Ingrese un numero valido.");
            }
        }
    }

    private double leerDecimal(String mensaje) {
        while (true) {
            String texto = leerTexto(mensaje);
            try {
                return Double.parseDouble(texto.replace(',', '.'));
            } catch (NumberFormatException ex) {
                System.out.println("Ingrese un numero valido.");
            }
        }
    }

    private LocalDate leerFecha(String mensaje) {
        while (true) {
            String texto = leerTexto(mensaje);
            try {
                return LocalDate.parse(texto);
            } catch (DateTimeParseException ex) {
                System.out.println("Ingrese una fecha valida con formato yyyy-MM-dd.");
            }
        }
    }

    private LocalDateTime leerFechaHora(String mensaje) {
        while (true) {
            String texto = leerTexto(mensaje);
            try {
                return LocalDateTime.parse(texto, FORMATO_FECHA_HORA);
            } catch (DateTimeParseException ex) {
                System.out.println("Ingrese fecha y hora con formato yyyy-MM-dd HH:mm.");
            }
        }
    }

    private boolean leerBooleano(String mensaje) {
        while (true) {
            String texto = leerTexto(mensaje).toLowerCase();
            if (texto.equals("si") || texto.equals("s") || texto.equals("true")) {
                return true;
            }
            if (texto.equals("no") || texto.equals("n") || texto.equals("false")) {
                return false;
            }
            System.out.println("Ingrese si o no.");
        }
    }
}