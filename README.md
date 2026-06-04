¿Qué responsabilidades asignaste a cada objeto?
RTA:
A la clase Envio le di las responsabilidades de agregar paquetes al mismo, iniciar el envío con preparación y la finalización con el método finalizar, avisándole a los paquetes que cambien su estado en el enum. Le di el método abstracto de calcular el costo de envío para que sus subclases lo sobreescriban, y la capacidad de retorno de la información de cada paquete.
A la clase Paquete le di la responsabilidad de cambiar su propio estado de distribución, guarda su propia información como id, descripción y peso, y pone sus atributos en privado para que nadie los pueda modificar desde afuera, exponiendo esa información solo a través de los getters.
En el Main se crean los objetos, se los relaciona con los métodos, se hace que interactúen entre sí, se muestran los resultados en consola y se maneja la lista de envíos.

¿Qué decisiones te resultaron difíciles?
La implementación del enum fue difícil, no tanto crearlo sino usarlo dentro del sistema. El calcular costos me costó la lógica del cálculo, no tanto lo abstracto. Y el ArrayList me costó el entendimiento de cómo funciona y se comporta.

¿Qué métodos decidiste hacer privados y por qué?
Los métodos en su totalidad son públicos porque son utilizados por otros objetos. Por ejemplo, cada tipo de envío utiliza calcularCosto(), en Envio se utilizan los métodos para preparar el paquete, y en el Main se llama a la mayoría de ellos.

¿Qué setters evitaste implementar y por qué?
En particular, el único momento en que podría haber usado algún setter fue en la distribución, pero lo evité para que no haya cambios manuales y así evitar saltear pasos y que se cometan errores.

¿Qué código tuviste que cambiar al incorporar herencia?
En este caso modifiqué la clase Envio para hacerla abstracta. Junto a eso, también creé el método abstracto calcularCosto() para luego sobreescribirlo en cada clase hija con su propia lógica de cálculo.