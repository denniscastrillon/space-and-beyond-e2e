# Space & Beyond — E2E Test Automation

End-to-end UI automation for the "Space & Beyond" booking flow on
[https://demo.testim.io/](https://demo.testim.io/), built with **Serenity BDD**
(Screenplay pattern) and **Cucumber**.

The full brief is in [`docs/Prueba Tecnica Automatizador.docx`](docs/Prueba%20Tecnica%20Automatizador.docx).

## Tech stack

| | |
|---|---|
| Language | Java 21 |
| Build / dependencies | Gradle 8.7 (wrapper) |
| Test framework | Serenity BDD 4.3.2 + Screenplay |
| BDD | Cucumber 7.31 (Gherkin) |
| Runner | JUnit 5 Platform (parallel by scenario) |
| Browser | Selenium 4.38 (Selenium Manager resolves the driver) |
| Assertions | AssertJ 3.26 |

## Requirements

- JDK 21 (`gradle.properties` points `org.gradle.java.home` at a local JDK 21 — adjust or
  remove it if `JAVA_HOME` already points at one; Gradle also auto-detects it via toolchain).
- Google Chrome installed.
- Internet access.

## Running the suite

```bash
./gradlew clean test                 # full regression suite (parallel)
./gradlew clean test -Pheadless      # headless
./gradlew test -Ptags="@filtro"      # a subset by tag
./gradlew test -Dcucumber.execution.parallel.config.fixed.parallelism=4
./gradlew bugReport                  # runs only the @bug scenarios (see below)
```

Report: `target/site/serenity/index.html`.

## Project layout

```
src/test/resources/
├── features/agendar_viaje.feature      Gherkin scenarios
├── serenity.conf                       WebDriver / environment config
├── junit-platform.properties           Cucumber + parallel execution
└── data/health-insurance.pdf           upload fixture
src/test/java/com/devco/spaceandbeyond/
├── runners/            AcceptanceTestSuite  (JUnit 5 entry point)
├── stepdefinitions/    Cucumber glue + Screenplay stage setup
├── tasks/              business-level Screenplay tasks
├── interactions/       low-level widget interactions (calendar, slider, dropdown)
├── questions/          Screenplay questions
├── ui/                 Target locators + PageObject for the base URL
└── model/              domain records (Traveller, TravelSearch)
docs/
├── casos-de-prueba.md   test cases (automated + written)
└── reporte-de-bugs.md   bug report
```

## Screenplay

```
Actor  ──▶  Ability (BrowseTheWeb, one browser per worker thread)
  ├── attemptsTo(Task)  ──▶  Interaction / Action  ──▶  Target
  └── asksFor(Question) ──▶  Target  ──▶  AssertJ
```

Test data is never hard-coded in the tasks — dates (as day offsets), party size, price
limits, traveller details, promo codes and the destination all come from the feature file.

## Parallel execution

Configured in `junit-platform.properties`: one thread per scenario, fixed pool of 3
(`-Dcucumber.execution.parallel.config.fixed.parallelism=N` to change it). Each scenario
gets its own browser (`serenity.restart.browser.for.each = scenario`), so scenarios never
share state.

## Bug scenarios

The brief assumes that pressing **PAY NOW** shows a *"Destination Booked"* message. It
doesn't (see [`docs/reporte-de-bugs.md`](docs/reporte-de-bugs.md), SPACE-001). The scenario
that asserts it carries the `@bug` tag and is excluded from the default run so the
regression stays green; run it on demand with `./gradlew bugReport`.

## Test cases & bug report

- [`docs/casos-de-prueba.md`](docs/casos-de-prueba.md) — 15 cases: 5 automated (critical
  path), 10 written (non-critical).
- [`docs/reporte-de-bugs.md`](docs/reporte-de-bugs.md) — 5 defects (1 blocker, 2 major, 2 minor).
