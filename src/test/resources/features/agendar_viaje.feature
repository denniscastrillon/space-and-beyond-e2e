# language: es
@viaje @espacial
Característica: Agendamiento de un viaje espacial en Space & Beyond

  Historia de usuario:
    Como Product Owner
    Quiero agendar un viaje en línea usando el filtro de precios
    Para entregar independencia a mis clientes

  Aplicación bajo prueba: https://demo.testim.io/
  Nota: los precios de los destinos son aleatorios en cada carga, por eso los
  escenarios que necesitan determinismo mueven el filtro a un valor conocido.

  Antecedentes:
    Dado que Paula quiere agendar un viaje espacial en Space & Beyond

  @critico @e2e @smoke @CP-01
  Esquema del escenario: Reserva de un destino diligenciando todo el checkout
    Cuando busca un viaje con los datos:
      | departingInDays | returningInDays | adults    | children |
      | <salida>        | <regreso>       | <adultos> | <ninos>  |
    Y presiona SELECT DESTINATION
    Y presiona LOAD MORE para mostrar todos los destinos
    Y filtra los destinos a un precio máximo de <precioMaximo> dólares
    Entonces todos los destinos visibles cuestan <precioMaximo> dólares o menos
    Cuando elige el destino "<destino>"
    Y diligencia los datos del viajero:
      | name      | email     | socialSecurityNumber | phoneNumber |
      | <nombre>  | <correo>  | <ssn>                | <telefono>  |
    Y carga el documento de salud "data/health-insurance.pdf"
    Y aplica el código promocional "<promo>"
    Y acepta los términos y condiciones
    Y presiona PAY NOW
    Entonces el total del pedido disminuye respecto al valor previo al código promocional

    Ejemplos:
      | salida | regreso | adultos | ninos | precioMaximo | destino | nombre           | correo                      | ssn         | telefono      | promo     |
      | 7      | 14      | 2       | 1     | 1800         | Tongli  | Dennis Castrillo | dennis.castrillo@correo.com | 123-45-6789 | +17871234567  | DEVCO10   |
      | 10     | 21      | 1       | 0     | 1800         | Madan   | Ana Herrera      | ana.herrera@correo.com      | 987-65-4321 | +1787 9876543 | VIAJA2024 |

  @critico @e2e @bug @SPACE-001 @CP-02
  Escenario: El sistema confirma la reserva mostrando "Destination Booked"
    Cuando busca un viaje con los datos:
      | departingInDays | returningInDays | adults | children |
      | 7               | 14              | 2      | 1        |
    Y presiona SELECT DESTINATION
    Y presiona LOAD MORE para mostrar todos los destinos
    Y filtra los destinos a un precio máximo de 1800 dólares
    Y elige el destino "Tongli"
    Y diligencia los datos del viajero:
      | name             | email                       | socialSecurityNumber | phoneNumber  |
      | Dennis Castrillo | dennis.castrillo@correo.com | 123-45-6789          | +17871234567 |
    Y carga el documento de salud "data/health-insurance.pdf"
    Y aplica el código promocional "DEVCO10"
    Y acepta los términos y condiciones
    Y presiona PAY NOW
    Entonces Paula ve el mensaje de confirmación "Destination Booked"

  @filtro @regresion @CP-03
  Esquema del escenario: El filtro de precios solo muestra destinos dentro del presupuesto
    Dado que Paula ha buscado un viaje y ve todos los destinos
    Cuando filtra los destinos a un precio máximo de <precioMaximo> dólares
    Entonces todos los destinos visibles cuestan <precioMaximo> dólares o menos
    Y al restaurar el filtro a 1800 dólares se vuelven a mostrar 9 destinos

    Ejemplos:
      | precioMaximo |
      | 400          |
      | 700          |
      | 1000         |

  @viajeros @regresion @CP-04
  Esquema del escenario: El resumen del viaje refleja la cantidad de viajeros
    Cuando busca un viaje con los datos:
      | departingInDays | returningInDays | adults    | children |
      | 5               | 12              | <adultos> | <ninos>  |
    Y presiona SELECT DESTINATION
    Entonces el resumen del viaje contiene "<resumen>"

    Ejemplos:
      | adultos | ninos | resumen     |
      | 1       | 0     | 1 traveler  |
      | 2       | 2     | 4 travelers |
      | 4       | 3     | 7 travelers |

  @validacion @regresion @CP-05
  Escenario: PAY NOW exige un teléfono que inicie con +1787 y 7 dígitos
    Cuando busca un viaje con los datos:
      | departingInDays | returningInDays | adults | children |
      | 7               | 14              | 1      | 0        |
    Y presiona SELECT DESTINATION
    Y elige el destino "Madan"
    Y diligencia los datos del viajero:
      | name        | email                  | socialSecurityNumber | phoneNumber   |
      | Luis Prueba | luis.prueba@correo.com | 111-22-3333          | +1999 1234567 |
    Entonces el botón PAY NOW permanece deshabilitado
    Cuando corrige el teléfono del viajero a "+17871234567"
    Entonces el botón PAY NOW queda habilitado
