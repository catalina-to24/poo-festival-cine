PROYECTO: Sistema de Gestion de Festival Internacional de Cine

Descripcion
Aplicacion de consola en Java para administrar un festival de cine: ediciones, secciones, peliculas, funciones, salas, butacas, ventas de entradas, abonos, jurados y evaluaciones.

Tecnologias
- Java 17
- Maven (estructura del proyecto)
- Persistencia por serializacion Java

Como ejecutar
1. Abrir el proyecto en un IDE con soporte Java.
2. Ejecutar la clase principal: src.app.Main.

Nota: si el entorno no tiene Maven instalado, se puede ejecutar igualmente desde el IDE corriendo Main.

Alcance funcional implementado
1. Gestion del festival
- Registrar ediciones.
- Crear secciones por edicion.
- Registrar salas con capacidad.
- Registrar butacas por sala, respetando capacidad.

2. Gestion de peliculas
- Registrar peliculas.
- Registrar directores.
- Registrar actores.
- Asociar peliculas a secciones.

3. Programacion de funciones
- Programar funcion por pelicula, sala, fecha y hora.
- Consultar funciones programadas.
- Evitar superposiciones en la misma sala.

4. Venta de entradas
- Registrar espectadores.
- Vender entradas con validacion de disponibilidad.
- Evitar duplicacion de butaca vendida.
- Consultar ocupacion de funcion y porcentaje de sala.
- Comprar y aplicar abono del festival.

5. Evaluaciones y ganadores
- Registrar jurados.
- Registrar evaluaciones.
- Consultar puntaje promedio por pelicula.
- Determinar ganadoras por seccion.

Supuestos
- El sistema administra un unico festival principal.
- Los datos se cargan al iniciar y se guardan al confirmar cambios.
- La interfaz es por consola.

Estructura de paquetes
- model: entidades del dominio.
- service: logica de negocio y validaciones.
- excepcion: excepciones de dominio.
- persistencia: carga y guardado en archivo.
- ui: menu e interaccion por consola.
- app: punto de entrada (Main).

Persistencia
- Estrategia: serializacion de objetos Java.
- Archivo: data/estado-festival.ser.

Decisiones de diseno
- Separacion de responsabilidades entre UI, servicios, dominio y persistencia.
- Uso de colecciones (ArrayList) para administrar entidades.
- Manejo de errores por excepciones de dominio.
- Uso de herencia en Persona y sus especializaciones (Actor, Director, Espectador, Jurado).

AbonoFestival
- Campos principales: codigo, cantidad de funciones, descuento, vigente.
- Comportamientos: validar uso, calcular descuento y consumir funciones.

Flujo de ejemplo
1. Registrar edicion, sala y butacas.
2. Registrar director, actor y pelicula.
3. Crear seccion y asociar pelicula.
4. Programar funcion.
5. Registrar espectador y comprar abono (opcional).
6. Vender entrada.
7. Registrar evaluacion y consultar ganadoras.
