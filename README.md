¿Qué responsabilidades asignaste a cada objeto?
RTA:
A la clase Envio le di las responsabilidades de agregar paquetes al mismo, iniciar el envío con la preparación de los paquetes y la finalización con el método entregar, avisándole a los paquetes que cambien su estado en el enum. Le di el método abstracto de calcular el costo de envío para que sus subclases lo sobreescriban, y la capacidad de retorno de la información de cada paquete y del historial de movimientos.
A la clase Paquete le di la responsabilidad de cambiar su propio estado de distribución, guarda su propia información como id, descripción y peso, y pone sus atributos en privado para que nadie los pueda modificar desde afuera, exponiendo esa información solo a través de los getters.
A la clase Sucursal le di la responsabilidad de administrar los envíos que tiene en custodia, validando que el envío esté realmente en la sucursal antes de despacharlo o entregarlo.
A la clase Movimiento le di la responsabilidad de representar un acontecimiento del recorrido de un envío, guardando la fecha, el tipo de movimiento y la sucursal donde ocurrió.
Al enum TipoMovimiento le di la responsabilidad de declarar qué movimientos existen y después de qué etapas puede registrarse cada uno.
Al enum EstadoPaquete le di la responsabilidad de declarar a qué estados puede avanzar cada estado del paquete.
En el Main se crean los objetos, se los relaciona con los métodos, se hace que interactúen entre sí y se muestran los resultados en consola. Las listas de envíos las administran el Cliente y la Sucursal, no el Main.

¿Qué decisiones te resultaron difíciles?
La implementación del enum fue difícil, no tanto crearlo sino usarlo dentro del sistema. El calcular costos me costó la lógica del cálculo, no tanto lo abstracto. Y el ArrayList me costó el entendimiento de cómo funciona y se comporta.
Con la corrección del N2.1 se sumó otra dificultad: decidir dónde vive cada regla. Al principio las validaciones y las transiciones estaban escritas con if dentro de cada método, y separarlas en métodos privados con nombre propio y en tablas declaradas dentro de los enums me costó bastante.

¿Qué métodos decidiste hacer privados y por qué?
En el TP1 los métodos eran casi todos públicos porque los utilizaba otro objeto. Con la corrección aparecieron los métodos privados auxiliares, que resuelven las reglas internas y no tienen que poder llamarse desde afuera. En Paquete quedó privado cambiarEstado, que es la única puerta de entrada al estado del paquete. En Envio quedaron privados hayLugar, todaviaNoSalio, tienePaquetes, getUltimaEtapa, estaEnEtapaDeRecepcion, puedeDespacharse, puedeEntregarse y registrarMovimiento, que dan nombre a las reglas del negocio, se reutilizan entre las operaciones y evitan repetir condiciones. En el Main quedaron privados mostrarHistorial, recibir, despachar y entregar, porque son ayudas internas del App. Desde afuera se siguen usando los mensajes de negocio, como agregarPaquete, recibir, despachar y entregar.

¿Qué setters evitaste implementar y por qué?
En particular, el único momento en que podría haber usado algún setter fue en la distribución, pero lo evité para que no haya cambios manuales y así evitar saltear pasos y que se cometan errores. Lo mismo hice con el historial, que se agrega solamente desde registrarMovimiento. Tampoco puse setters para los datos de identidad (id, nombre, dirección y teléfono) porque no tienen por qué cambiar después de crear el objeto, y las listas que se devuelven (getPaquetes, getHistorial y getEnviosEnSucursal) van envueltas en Collections.unmodifiableList para que nadie las modifique desde afuera.

¿Qué código tuviste que cambiar al incorporar herencia?
En este caso modifiqué la clase Envio para hacerla abstracta. Junto a eso, también creé el método abstracto calcularCosto() para luego sobreescribirlo en cada clase hija con su propia lógica de cálculo.

TP N°2.1 - Correcciones y completado

Clase Movimiento / RegistroSeguimiento
El tipo de movimiento no se registra simplemente como un String: es el enum TipoMovimiento (RECIBIDO, DESPACHADO y ENTREGADO) y cada acontecimiento se representa con un objeto Movimiento del dominio, que guarda la fecha, el tipo y la sucursal donde ocurrió. Esa es la información necesaria para reconstruir el recorrido del envío, y la secuencia completa la guarda el Envio en su historial. El TP nombra esta clase como Movimiento o RegistroSeguimiento, y usé Movimiento porque es el nombre que ya tenía el TP2.

Consultas sobre el historial
El sistema permite consultar el recorrido de un envío, y para eso el envío devuelve la información como dato y el App la muestra. getHistorial() devuelve el historial completo, getSucursalesEnHistorial() informa por qué sucursales pasó el envío y getUltimoMovimiento() devuelve el último movimiento registrado. Ninguna clase del paquete modelo tiene un System.out: el Main es el único que imprime, y lo hace con los datos que le devuelven esos métodos, por ejemplo App -> Envio -> getUltimoMovimiento().

Excepciones
Las operaciones que no respetan las reglas del sistema se impiden con excepciones del dominio. OperacionInvalidaException es la base y representa una operación que rompe una regla de negocio, como superar el máximo de 3 paquetes o querer recibir un envío sin paquetes. TransicionInvalidaException se lanza cuando el objeto está en un estado desde el que no puede pasar al que se le pide, por ejemplo entregar un paquete que todavía no fue despachado, despachar un envío que ya fue entregado, recibir un envío que está en otra etapa o registrar un movimiento incoherente. EnvioInexistenteException se lanza cuando se intenta operar sobre un envío que no está en esa sucursal. Los casos que quedaron cubiertos y probados en el Main son: máximo de paquetes, envío sin paquetes, recibir un envío dos veces, despachar sin haberlo recibido, despachar un envío ya entregado, agregar paquetes a un envío que ya salió, entregar un paquete que no fue despachado y operar sobre un envío inexistente.

Validaciones y métodos privados auxiliares
No se concentra toda la lógica en un método mediante variables auxiliares booleanas. Cada comprobación quedó en un método privado con nombre propio que se reutiliza entre las operaciones, como hayLugar, todaviaNoSalio, tienePaquetes, estaEnEtapaDeRecepcion, puedeDespacharse y puedeEntregarse, además de getUltimaEtapa y registrarMovimiento. Por ejemplo, agregarPaquete ya no calcula nada por su cuenta: le pregunta a todaviaNoSalio() si el envío todavía está en la sucursal de origen y a hayLugar() si queda espacio, y recién ahí agrega el paquete.

Condiciones complejas
La condición larga del TP2 se eliminó. La coherencia de cada movimiento está declarada como dato en el enum TipoMovimiento: RECIBIDO puede registrarse al principio o después de DESPACHADO, DESPACHADO después de RECIBIDO y ENTREGADO después de RECIBIDO. El envío ya no conoce esa regla, solamente le pregunta al tipo de movimiento con puedeRegistrarseDespuesDe(getUltimaEtapa()), y el caso de un envío sin movimientos lo resuelve puedeSerElPrimero().

Avance de estados
El cambio de estado no se resuelve con una cadena extensa de if/else ni con un switch. Cada estado del enum EstadoPaquete declara a qué estados puede avanzar: desde RECIBIDO se va a EN_PREPARACION, desde EN_PREPARACION a EN_DISTRIBUCION o ENTREGADO, desde EN_DISTRIBUCION a EN_PREPARACION o ENTREGADO, y ENTREGADO es un estado final. El paquete es el único responsable de su estado: preparar(), distribuir() y entregar() delegan en el método privado cambiarEstado(), que le pregunta al estado actual si la transición es válida y lanza una TransicionInvalidaException si no lo es. El App nunca toca el estado directamente: solo envía mensajes y la cadena de colaboración es Sucursal -> Envio -> Paquete.

Responsabilidades: qué objeto debería ser responsable de cada cosa
El estado del paquete lo protege el Paquete, porque es quien lo conoce; nadie de afuera necesita saber qué transición es válida, se le pregunta al propio estado. La coherencia del historial la conoce TipoMovimiento, porque define qué movimientos existen y en qué orden pueden ocurrir. La custodia de los envíos la lleva la Sucursal, que sabe qué envíos tiene y valida que el envío esté ahí antes de despacharlo o entregarlo. El Envio coordina a sus paquetes y su historial, pero no decide por ellos. Y el App no manipula datos internos ni conoce las reglas: pide operaciones y muestra lo que le devuelven.