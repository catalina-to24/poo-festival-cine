---
description: Describe when these instructions should be loaded by the agent based on task context
# applyTo: 'Describe when these instructions should be loaded by the agent based on task context' # when provided, instructions will automatically be added to the request context when the pattern matches an attached file
---

## Lineamientos del proyecto

Estas instrucciones aplican al trabajo sobre el sistema del festival de cine y deben respetarse al generar código, responder preguntas o revisar cambios.

## Alcance funcional

- Incorporar la lógica de negocio correspondiente a los requerimientos funcionales.
- Mantener coherencia con el dominio del festival de cine y extender el modelo con funcionalidades reales cuando sea necesario.
- Integrar cualquier nueva capacidad con los menús y servicios existentes.

## Reglas de diseño y arquitectura

- Respetar el diseño orientado a objetos.
- Mantener bajo acoplamiento y alta cohesión.
- Evitar duplicación innecesaria de código.
- Encapsular apropiadamente los atributos y validar las reglas del dominio.
- No resolver la lógica del sistema en la UI ni en el main.
- Favorecer una implementación desacoplada de la lógica de negocio.

## Conceptos obligatorios

Aplicar correctamente los siguientes conceptos según corresponda al problema:

- Herencia.
- Polimorfismo.
- Encapsulamiento.
- Colecciones.
- Manejo de excepciones.
- Persistencia en archivos.

Se espera que el polimorfismo se use para modelar comportamientos variables del dominio cuando corresponda, por ejemplo en distintos tipos de evaluaciones, categorías de premios o estrategias de cálculo. Evitar soluciones basadas exclusivamente en estructuras condicionales extensas.

## Organización del proyecto

- Organizar el proyecto en paquetes coherentes según la responsabilidad de cada clase.
- Usar una separación clara entre, por ejemplo: modelo, servicios, excepciones, persistencia, ui y app.
- La lógica principal debe vivir en servicios o clases de dominio, no en la interfaz de usuario.
- Las clases de persistencia deben encargarse exclusivamente de leer y escribir información.

## Excepciones

- Usar excepciones checked y/o unchecked según criterio de diseño.
- Manejar errores de forma explícita y con mensajes claros.
- Validar entradas y estados inválidos para proteger las reglas del dominio.

## Persistencia

- Persistir la información relevante para conservar los datos entre ejecuciones.
- Cargar automáticamente los datos al iniciar el sistema.
- Guardar los cambios realizados para evitar pérdida de información.
- La estrategia de persistencia queda a criterio del alumno: archivos de texto, serialización, CSV u otro mecanismo justificado.
- Las clases de persistencia deben limitarse a recuperar y almacenar datos, sin mezclar lógica de negocio.

## Interfaz de usuario

- Proveer una interfaz de usuario por consola.
- Permitir la interacción mediante menús.
- Mantener la UI simple y enfocada en invocar servicios, sin concentrar lógica de negocio.

## Criterios de calidad

- Mantener el sistema desacoplado y fácil de extender.
- Reutilizar código y evitar condicionales innecesarios cuando un diseño polimórfico sea más adecuado.
- Integrar nuevas funcionalidades de manera consistente con el modelo existente.

## Requerimientos funcionales mínimos

El sistema debe implementar, como mínimo, los siguientes servicios o funcionalidades.

### 1. Gestión del festival

- Registrar nuevas ediciones del festival.
- Definir secciones dentro de una edición.
- Registrar salas de cine y sus capacidades.
- Registrar butacas asociadas a las salas.

### 2. Gestión de películas

- Registrar películas.
- Asociar películas a una o más secciones.
- Registrar directores.
- Registrar actores.
- Asociar directores y actores a las películas.

### 3. Programación de funciones

- Programar funciones indicando película, sala, fecha y horario.
- Consultar funciones programadas.
- Verificar disponibilidad de butacas para una función.
- Evitar situaciones inválidas, como funciones superpuestas en una misma sala, datos inconsistentes o superar la capacidad disponible.

### 4. Venta de entradas

- Registrar espectadores.
- Vender entradas para funciones.
- Asignar butacas disponibles.
- Validar disponibilidad antes de confirmar la compra.
- Evitar ventas duplicadas de una misma butaca.
- Consultar ocupación de una función.
- Obtener porcentaje de ocupación de sala.
- Permitir la compra de abonos del festival como beneficio especial para asistir a varias funciones, obteniendo descuentos o ventajas al momento de comprar entradas.

### 5. Evaluación y premios

- Registrar jurados.
- Registrar evaluaciones o puntajes otorgados a películas.
- Calcular el puntaje promedio de una película.
- Determinar películas ganadoras por sección o categoría.

Se espera que el sistema aproveche el polimorfismo para modelar comportamientos variables del dominio cuando corresponda, por ejemplo distintos tipos de evaluaciones, categorías de premios o estrategias de cálculo, evitando soluciones basadas exclusivamente en estructuras condicionales extensas.

## Interfaz de usuario por consola

La interacción con el sistema deberá realizarse mediante una interfaz por consola. El sistema deberá presentar menús claros y organizados que permitan acceder a las distintas funcionalidades.

Ejemplo de estructura esperada:

```text
===== SISTEMA DE GESTIÓN FESTIVAL DE CINE =====
1. Registrar edición
2. Registrar película
3. Registrar director
4. Registrar actor
5. Crear sección
6. Programar función
7. Registrar espectador
8. Vender entrada
9. Consultar ocupación de función
10. Registrar evaluación
11. Consultar puntaje promedio
12. Determinar ganadores
0. Salir
Seleccione una opción:
```

La interfaz deberá:

- Validar entradas del usuario.
- Informar errores de forma clara.
- Evitar interrupciones abruptas del sistema.
- Mantener consistencia en la navegación.
- No resolver la lógica del negocio ni en la interfaz ni en la clase main.

## Guía de implementación sugerida

El proyecto debería organizarse en paquetes coherentes según la responsabilidad de cada clase. La estructura recomendada es la siguiente:

### `model`

- Mantener aquí las entidades y objetos del dominio.
- Las clases actuales del proyecto pueden continuar en este paquete: `Festival`, `EdicionFestival`, `Seccion`, `Sala`, `Butaca`, `Pelicula`, `Director`, `Actor`, `Funcion`, `Espectador`, `Entrada`, `Compra`, `AbonoFestival`, `Jurado`, `Evaluacion`, `Premio` y `Organizador`.
- Estas clases deben representar el estado y el comportamiento propio del dominio.
- Evitar que estas clases dependan de la UI o de la persistencia.

### `service`

- Concentrar la lógica de negocio en servicios específicos.
- Separar por responsabilidad, por ejemplo: `FestivalService`, `PeliculaService`, `FuncionService`, `VentaService`, `EvaluacionService` y, si corresponde, `AbonoService`.
- Los servicios deben coordinar entidades, validar reglas y usar colecciones para administrar la información del sistema.
- La UI no debe contener lógica de negocio que pertenezca a esta capa.

### `persistencia`

- Crear aquí las clases responsables de leer y escribir datos.
- Estas clases deben limitarse exclusivamente a persistir y recuperar información.
- La estrategia puede ser archivos de texto, CSV, serialización u otra alternativa justificada.
- La carga inicial y el guardado de cambios deben quedar encapsulados en esta capa o en componentes que la utilicen.

### `ui`

- Implementar aquí la interacción por consola.
- Organizar menús claros para las funcionalidades del sistema.
- La UI solo debe pedir datos, mostrar resultados y delegar en servicios.
- Manejar errores de entrada sin resolver reglas de negocio.

### `app`

- Ubicar aquí la clase principal o punto de entrada.
- El `main` debe iniciar la aplicación, cargar datos y lanzar la UI.
- No debe contener lógica de negocio.

### `excepciones`

- Definir excepciones propias para representar errores del dominio y de validación.
- Ejemplos: entidad no encontrada, datos inválidos, capacidad insuficiente, función superpuesta, entrada duplicada o butaca no disponible.
- Usar checked o unchecked según convenga al diseño.

## Orden recomendado de desarrollo

1. Definir o ajustar el modelo del dominio.
2. Implementar servicios con validaciones y reglas de negocio.
3. Agregar excepciones del dominio.
4. Crear persistencia para cargar y guardar la información.
5. Implementar la UI por consola y conectar los menús con los servicios.

## Criterios de implementación esperados

- No duplicar lógica entre servicios, UI y persistencia.
- Usar polimorfismo cuando existan comportamientos variables del dominio.
- Mantener encapsulamiento estricto en el modelo.
- Validar datos antes de operar sobre el estado del sistema.
- Persistir la información necesaria para continuar la ejecución entre sesiones.