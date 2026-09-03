# Casos de prueba — Space & Beyond (https://demo.testim.io/)

Historia: *Como Product Owner quiero agendar un viaje en línea usando el filtro de
precios para entregar independencia a mis clientes.*

Leyenda de prioridad:
- **Ruta crítica** → automatizado (Serenity Screenplay + Cucumber).
- **No crítica** → caso de prueba escrito (ejecución manual / backlog de automatización).

| ID | Título | Tipo | Estado |
|----|--------|------|--------|
| CP-01 | Reserva E2E de un destino diligenciando todo el checkout | Ruta crítica | Automatizado (`@critico @e2e @smoke`) |
| CP-02 | El sistema confirma la reserva con el mensaje "Destination Booked" | Ruta crítica | Automatizado (`@critico @e2e @bug`) — **falla por defecto SPACE-001** |
| CP-03 | El filtro de precios solo muestra destinos dentro del presupuesto (Scenario Outline) | Ruta crítica | Automatizado (`@filtro @regresion`) |
| CP-04 | El resumen del viaje refleja la cantidad de viajeros (Scenario Outline) | Ruta crítica | Automatizado (`@viajeros @regresion`) |
| CP-05 | PAY NOW exige un teléfono que inicie con +1787 y 7 dígitos | Ruta crítica | Automatizado (`@validacion @regresion`) |
| CP-06 | El botón LOAD MORE muestra todos los destinos | No crítica | Escrito |
| CP-07 | Validación de campo obligatorio: Nombre | No crítica | Escrito |
| CP-08 | Validación de formato: Email | No crítica | Escrito |
| CP-09 | Validación de formato: Social Security Number | No crítica | Escrito |
| CP-10 | El check "I agree to the terms and conditions" es obligatorio para pagar | No crítica | Escrito |
| CP-11 | La carga del documento de salud es obligatoria para pagar | No crítica | Escrito |
| CP-12 | El código promocional inválido / vacío no altera el total | No crítica | Escrito |
| CP-13 | Persistencia de fechas y viajeros en el resumen del checkout | No crítica | Escrito |
| CP-14 | Filtro por "Launch" y "Planet color" | No crítica | Escrito |
| CP-15 | Reingreso: volver atrás desde el checkout mantiene la selección | No crítica | Escrito |

---

## Casos automatizados (ruta crítica)

### CP-01 — Reserva E2E de un destino diligenciando todo el checkout
**Objetivo:** validar el flujo completo feliz hasta presionar PAY NOW.
**Precondición:** navegador en `https://demo.testim.io/`.
**Datos (parametrizados):** salida +7 d, regreso +14 d, 2 adultos, 1 niño,
precio máximo 1800, destino "Tongli", nombre/email/SSN/teléfono válidos,
`data/health-insurance.pdf`, código promocional `DEVCO10`.

| # | Paso | Resultado esperado |
|---|------|--------------------|
| 1 | Ingresar fecha de salida y de regreso en el calendario | Los campos muestran las fechas elegidas |
| 2 | Seleccionar 2 adultos y 1 niño | Los dropdowns reflejan la selección |
| 3 | Presionar **SELECT DESTINATION** | Navega a `/destinations`; el resumen dice "3 travelers, ..." |
| 4 | Presionar **LOAD MORE** | Se muestran los 9 destinos |
| 5 | Mover la barra de precios a un máximo de 1800 | Se mantienen visibles destinos con precio ≤ 1800 |
| 6 | Verificar precios visibles | Todos ≤ 1800 |
| 7 | Presionar **BOOK** en "Tongli" | Navega a `/checkout` con el precio de Tongli en el resumen |
| 8 | Diligenciar Name, Email Address, Social Security Number, Phone Number (`+1787` + 7 dígitos) | Sin errores de validación; **PAY NOW** se habilita |
| 9 | Cargar el archivo en la drop-zone | Se muestra la vista previa del documento |
| 10 | Ingresar el código promocional y presionar **APPLY** | El **Total** disminuye respecto al valor previo |
| 11 | Marcar **I agree to the terms and conditions** | El check queda activo |
| 12 | Presionar **PAY NOW** | (Esperado de negocio: mensaje "Destination Booked" — ver CP-02) |
| 13 | Verificar el Total | El Total con descuento es menor al Total sin código promocional |

### CP-02 — Confirmación "Destination Booked"
**Objetivo:** al presionar PAY NOW con el formulario válido, el sistema confirma la
reserva mostrando *"Destination Booked"* (supuesto del enunciado).
**Pasos:** CP-01 pasos 1–12.
**Resultado esperado:** aparece un mensaje visible que contiene `Destination Booked`.
**Resultado actual:** no aparece ningún mensaje; PAY NOW no tiene efecto. → **Defecto SPACE-001** (ver reporte de bugs).
Automatizado con el tag `@bug`; se ejecuta con `./gradlew bugReport`.

### CP-03 — Filtro de precios (Scenario Outline)
**Objetivo:** el filtro solo deja visibles destinos con precio ≤ máximo.
**Ejemplos (parametrizados):** máximo = 400, 700, 1000.

| # | Paso | Resultado esperado |
|---|------|--------------------|
| 1 | Buscar un viaje y ver los 9 destinos | 9 tarjetas visibles |
| 2 | Mover la barra de precios al `<precioMaximo>` | La lista se recorta |
| 3 | Verificar precios visibles | Todos ≤ `<precioMaximo>` |
| 4 | Restaurar la barra a 1800 | Vuelven a mostrarse los 9 destinos |

### CP-04 — Resumen de viajeros (Scenario Outline)
**Objetivo:** el encabezado del gallery/checkout muestra "N traveler(s)".
**Ejemplos:** (1 adulto, 0 niños → "1 traveler"), (2, 2 → "4 travelers"), (4, 3 → "7 travelers").

| # | Paso | Resultado esperado |
|---|------|--------------------|
| 1 | Seleccionar `<adultos>` y `<ninos>` | Dropdowns actualizados |
| 2 | Presionar SELECT DESTINATION | El resumen contiene el texto `<resumen>` |

### CP-05 — Validación del teléfono
**Objetivo:** PAY NOW solo se habilita con un teléfono `+1787` + 7 dígitos.

| # | Paso | Resultado esperado |
|---|------|--------------------|
| 1 | Elegir un destino y diligenciar el formulario con teléfono `+1999 1234567` | PAY NOW permanece **deshabilitado** |
| 2 | Corregir el teléfono a `+17871234567` | PAY NOW queda **habilitado** |

---

## Casos NO críticos (escritos)

### CP-06 — LOAD MORE muestra todos los destinos
1. Buscar un viaje y presionar SELECT DESTINATION → se muestran 6 destinos.
2. Presionar LOAD MORE → se muestran 9 destinos.
3. **Esperado:** el botón LOAD MORE desaparece (no hay más para cargar).
   **Actual:** el botón permanece visible aunque deshabilitado → **SPACE-004**.

### CP-07 — Campo obligatorio: Nombre
1. En el checkout, dejar **Name** vacío y diligenciar el resto con datos válidos.
2. **Esperado:** PAY NOW deshabilitado mientras Name esté vacío; al completarlo (≤ 30 caracteres, contador "n/30"), se habilita.

### CP-08 — Formato de Email
1. Ingresar `correo-invalido` en **Email Address** y salir del campo.
2. **Esperado:** se muestra "Enter a valid e-mail address." y PAY NOW no se habilita.
3. Corregir a `persona@dominio.com` → el error desaparece.

### CP-09 — Formato de Social Security Number
1. Ingresar `12345` en **Social Security Number** y salir del campo.
2. **Esperado:** se muestra "Enter a valid Social Security number (xxx-xx-xxxx)." y PAY NOW no se habilita.
3. Corregir a `123-45-6789` → el error desaparece.

### CP-10 — Términos y condiciones obligatorios
1. Diligenciar todo el checkout con datos válidos y cargar el documento, **sin** marcar el check.
2. **Esperado:** PAY NOW deshabilitado hasta marcar "I agree to the terms and conditions".
3. **Actual:** PAY NOW se habilita sin el check → **SPACE-002**.

### CP-11 — Carga de documento obligatoria
1. Diligenciar todo el checkout con datos válidos y marcar el check, **sin** cargar archivo.
2. **Esperado:** PAY NOW deshabilitado hasta cargar el documento en la drop-zone.
3. **Actual:** PAY NOW se habilita sin documento → **SPACE-002**.

### CP-12 — Código promocional inválido / vacío
1. Dejar el campo de código vacío → el botón APPLY está deshabilitado.
2. Ingresar un código y aplicar → el **Total** cambia.
3. **Observación:** el descuento aplicado es un valor variable y no se muestra el
   monto/porcentaje en el resumen → **SPACE-005**.

### CP-13 — Persistencia de fechas y viajeros
1. Buscar un viaje con fechas y viajeros específicos, elegir un destino.
2. **Esperado:** el bloque "Order Summary" del checkout muestra el rango de fechas
   y la cantidad de viajeros seleccionados.

### CP-14 — Filtros "Launch" y "Planet color"
1. En el gallery, seleccionar un valor de **Launch** y uno de **Planet color**.
2. **Esperado:** la lista de destinos se filtra de forma consistente con la selección.

### CP-15 — Volver atrás desde el checkout
1. Desde `/checkout`, navegar hacia atrás en el navegador.
2. **Esperado:** el gallery mantiene el filtro de precio y la selección de viajeros previos.
