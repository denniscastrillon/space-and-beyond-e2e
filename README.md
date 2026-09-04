# Space & Beyond — Automatización de Pruebas E2E

Automatización end-to-end del flujo de reserva de "Space & Beyond" en
[https://demo.testim.io/](https://demo.testim.io/), construida con **Serenity BDD**
(patrón Screenplay) y **Cucumber**.

## Contexto

El flujo cubierto: búsqueda de viaje (fechas, adultos/niños) → selección de destino
(filtro de precios, LOAD MORE) → checkout (datos del viajero, carga de documento, código
promocional, términos y condiciones) → PAY NOW. Todos los datos de prueba están
parametrizados desde Gherkin; nada queda escrito directamente en el código.

## Stack

| | |
|---|---|
| Lenguaje | Java 21 |
| Build / dependencias | Gradle 8.7 (wrapper) |
| Framework de pruebas | Serenity BDD 4.3.2 + Screenplay |
| BDD | Cucumber 7.31 (Gherkin en español) |
| Runner | JUnit 5 Platform (paralelo por escenario) |
| Navegador | Selenium 4.38 (Selenium Manager resuelve el driver) — Chrome, Edge y Firefox |
| Aserciones | AssertJ 3.26 |

## Requisitos

- JDK 21 (`gradle.properties` puede fijar `org.gradle.java.home`; Gradle también lo
  detecta solo vía toolchain).
- Google Chrome instalado.
- Conexión a Internet.

## Ejecución

```bash
./gradlew clean test                 # suite completa de regresión (en paralelo)
./gradlew clean test -Pheadless      # modo headless
./gradlew test -Ptags="@filtro"      # un subconjunto por tag
./gradlew test -Dcucumber.execution.parallel.config.fixed.parallelism=4
./gradlew bugReport                  # solo los escenarios @bug (ver abajo)
```

Reporte: `target/site/serenity/index.html`.

### Navegador

Por defecto la suite corre en Chrome. Se puede elegir otro navegador con
`-Dwebdriver.driver`, sin tocar código ni configuración:

```bash
./gradlew clean test -Dwebdriver.driver=firefox
./gradlew clean test -Dwebdriver.driver=edge
```

Cada navegador tiene su propio bloque de capacidades W3C en `serenity.conf`
(`goog:chromeOptions`, `ms:edgeOptions`, `moz:firefoxOptions`); Selenium Manager
resuelve automáticamente el driver correspondiente.

## Estructura

```
src/test/resources/
├── features/agendar_viaje.feature      escenarios Gherkin
├── serenity.conf                       configuración de WebDriver / entorno
├── junit-platform.properties           Cucumber + ejecución en paralelo
└── data/health-insurance.pdf           archivo para la carga de documento
src/test/java/com/devco/spaceandbeyond/
├── runners/            AcceptanceTestSuite  (punto de entrada JUnit 5)
├── stepdefinitions/    glue de Cucumber + montaje del actor Screenplay
├── tasks/              tareas Screenplay de negocio
├── interactions/       interacciones de bajo nivel (calendario, slider, dropdown)
├── questions/          preguntas Screenplay
├── ui/                 localizadores (Target) + PageObject de la URL base
└── model/              records de dominio (Traveller, TravelSearch)
docs/
├── casos-de-prueba.md   casos de prueba (automatizados + escritos)
└── reporte-de-bugs.md   reporte de bugs
```

## Screenplay

```
Actor  ──▶  Ability (BrowseTheWeb, un navegador por hilo)
  ├── attemptsTo(Task)  ──▶  Interaction / Action  ──▶  Target
  └── asksFor(Question) ──▶  Target  ──▶  AssertJ
```

## Ejecución en paralelo

Configurada en `junit-platform.properties`: un hilo por escenario, pool fijo de 3
(`-Dcucumber.execution.parallel.config.fixed.parallelism=N` para cambiarlo). Cada
escenario tiene su propio navegador (`serenity.restart.browser.for.each = scenario`),
por lo que no comparten estado.

## Escenarios de bug

Se asume que al presionar **PAY NOW** el sistema debe mostrar el mensaje
*"Destination Booked"*. No ocurre (ver [`docs/reporte-de-bugs.md`](docs/reporte-de-bugs.md),
SPACE-001). El escenario que lo verifica lleva el tag `@bug` y queda excluido de la
ejecución por defecto para que la regresión quede en verde; se ejecuta bajo demanda con
`./gradlew bugReport`.

## Casos de prueba y reporte de bugs

- [`docs/casos-de-prueba.md`](docs/casos-de-prueba.md) — 15 casos: 5 automatizados
  (ruta crítica), 10 escritos (no crítica).
- [`docs/reporte-de-bugs.md`](docs/reporte-de-bugs.md) — 5 defectos (1 blocker, 2 major, 2 minor).
