# Reporte de Bugs — Space & Beyond (https://demo.testim.io/)

| Entorno | Valor |
|---|---|
| Aplicación | https://demo.testim.io/ ("Space & Beyond") |
| Navegador | Google Chrome 151–152 (headed y `--headless=new`) |
| SO | Windows 11 |
| Fecha de detección | 2026-09-03 |
| Build de pruebas | Serenity BDD 4.3.2 + Screenplay + Cucumber 7.31.0 (Java 21) |

Severidad: **Blocker** > **Major** > **Minor**.

---

## SPACE-001 — PAY NOW no confirma la reserva ("Destination Booked" nunca aparece)

- **Severidad:** Blocker
- **Escenario automatizado:** `agendar_viaje.feature` → *"El sistema confirma la reserva mostrando \"Destination Booked\""* (`@bug`, `@critico`, `@e2e`).
- **Ejecución:** `./gradlew bugReport`

### Pasos para reproducir
1. Abrir `https://demo.testim.io/`.
2. Ingresar fechas de salida y regreso; seleccionar adultos y niños.
3. Presionar **SELECT DESTINATION** y luego **LOAD MORE**.
4. Presionar **BOOK** en cualquier destino.
5. En el checkout, diligenciar **Name**, **Email Address**, **Social Security Number**
   (`xxx-xx-xxxx`) y **Phone Number** (`+1787` + 7 dígitos).
6. Cargar un archivo en la drop-zone.
7. Ingresar un código promocional y presionar **APPLY**.
8. Marcar **I agree to the terms and conditions**.
9. Presionar **PAY NOW**.

### Resultado esperado
El sistema muestra un mensaje visible que indica **"Destination Booked"**
(supuesto explícito del enunciado de la prueba).

### Resultado actual
Al presionar **PAY NOW** no ocurre nada perceptible:
- No se muestra ningún mensaje de confirmación ("Destination Booked" no aparece en el DOM).
- No hay navegación ni cambio de URL (permanece en `/checkout`).
- No se dispara ninguna petición de red ni log en consola.

### Evidencia
- Aserción del test: `Expecting actual: "" to contain: "Destination Booked"`.
- Screenshot y `page source` del fallo en `target/site/serenity/` (reporte del escenario `@bug`).

### Impacto
El usuario **no puede completar una reserva**. Es un defecto que bloquea la
funcionalidad central del producto.

---

## SPACE-002 — PAY NOW se habilita sin aceptar términos ni cargar el documento obligatorio

- **Severidad:** Major

### Pasos para reproducir
1. Llegar al checkout de cualquier destino.
2. Diligenciar únicamente **Name**, **Email Address**, **Social Security Number** y
   **Phone Number** con datos válidos.
3. **No** marcar el check de términos y condiciones.
4. **No** cargar ningún archivo en la drop-zone.

### Resultado esperado
**PAY NOW** permanece deshabilitado: el check de términos y la carga del documento
de salud se presentan como obligatorios en la UI.

### Resultado actual
**PAY NOW** se habilita en cuanto los 4 campos de texto son válidos, sin importar
el check ni el archivo. (El defecto SPACE-001 impide comprobar qué haría el pago,
pero la habilitación del botón ya es incorrecta.)

### Impacto
Se puede intentar pagar sin consentimiento legal registrado ni el documento
requerido; inconsistencia entre las reglas de la UI y su comportamiento real.

---

## SPACE-003 — La validación del teléfono depende del idioma del navegador

- **Severidad:** Major

### Pasos para reproducir
1. Configurar el navegador con idioma español (p. ej. `es-CO`) — comportamiento por
   defecto en equipos en español.
2. Llegar al checkout y escribir en **Phone Number** un número válido de Puerto Rico:
   `+17871234567` (código `+1`, área `787`, 7 dígitos), tal como pide el enunciado.
3. Salir del campo.

### Resultado esperado
El número `+1787` + 7 dígitos se acepta y **PAY NOW** se habilita.

### Resultado actual
Con idioma `es-*` el campo muestra **"Enter a valid ES phone number."** y trata el
número como español (región ES), rechazándolo. El mismo número **sí** se acepta con
el navegador en `en-US`.
> En la suite se fuerza `--lang=en-US` / `intl.accept_languages=en-US` para que el
> caso de la ruta crítica sea determinista; el bug permanece para usuarios reales
> con navegador en español.

### Impacto
La región de validación del teléfono no debería derivarse del idioma del navegador
para un formulario que exige explícitamente un prefijo `+1787`. Usuarios legítimos
con navegador en español no pueden pagar.

---

## SPACE-004 — El botón LOAD MORE no desaparece al cargar todos los destinos

- **Severidad:** Minor

### Pasos para reproducir
1. Presionar **SELECT DESTINATION** → se muestran 6 destinos.
2. Presionar **LOAD MORE** → se muestran los 9 destinos.

### Resultado esperado
El botón **LOAD MORE** desaparece cuando ya no hay más destinos por cargar.

### Resultado actual
El botón permanece visible en estado deshabilitado. Un usuario puede intentar
hacer clic sin obtener respuesta.

### Impacto
Menor / cosmético; genera confusión sobre si hay más contenido disponible.

---

## SPACE-005 — Descuento del código promocional inconsistente y no visible

- **Severidad:** Minor

### Pasos para reproducir
1. En el checkout, anotar el **Total** (p. ej. `$1,183.46` para 1 viajero).
2. Ingresar un código promocional cualquiera y presionar **APPLY**.
3. Repetir el flujo en una nueva sesión con el mismo precio base y otro código.

### Resultado esperado
El descuento aplicado es consistente y su monto/porcentaje se muestra en el
resumen del pedido (línea "Descuento" o similar).

### Resultado actual
- Cualquier cadena se acepta como código válido (no hay lista de códigos).
- El descuento varía de forma aparentemente aleatoria entre ejecuciones
  (se observaron ~10%, ~14% y ~17% sobre el mismo precio base con distintos códigos).
- El resumen solo actualiza el **Total**; nunca muestra el monto descontado.

### Impacto
El usuario no sabe cuánto ahorró ni por qué; el total no es reproducible.

---

## Resumen

| ID | Severidad | Área | Estado |
|----|-----------|------|--------|
| SPACE-001 | Blocker | Checkout / Pago | Abierto — cubierto por test `@bug` |
| SPACE-002 | Major | Checkout / Validaciones | Abierto |
| SPACE-003 | Major | Checkout / Validación de teléfono | Abierto (mitigado en la suite con `--lang=en-US`) |
| SPACE-004 | Minor | Gallery / LOAD MORE | Abierto |
| SPACE-005 | Minor | Checkout / Código promocional | Abierto |
