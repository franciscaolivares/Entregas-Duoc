# SpeedFast

Actividad de la **Semana 5 de Desarrollo Orientado a Objetos II**: “Sincronizando procesos en sistemas concurrentes”.

Esta entrega continúa el proyecto realizado durante las semanas anteriores. Se mantiene la jerarquía de pedidos y las interfaces creadas en la Semana 3. Además, se incorpora una **zona de carga compartida** y mecanismos de sincronización para que varios repartidores retiren pedidos de forma segura, evitando entregas duplicadas.

## Conceptos aplicados

- Clase abstracta `Pedido` con atributos comunes.
- Identificador `id` de tipo `int`, dirección de entrega y estado del pedido.
- Método implementado `mostrarResumen()`.
- Método abstracto `calcularTiempoEntrega()`.
- Herencia mediante las clases `PedidoComida`, `PedidoEncomienda` y `PedidoExpress`.
- Sobrescritura de métodos con una fórmula distinta para cada pedido.
- Polimorfismo mediante referencias de tipo `Pedido` en las listas de pedidos.
- Interfaces `Despachable`, `Cancelable` y `Rastreable` en los tres tipos de pedido.
- Conservación de los métodos de reserva, despacho, cancelación e historial de las semanas anteriores.
- Enum `EstadoPedido` con los estados `PENDIENTE`, `EN_REPARTO` y `ENTREGADO`.
- Clase `ZonaDeCarga` que almacena los pedidos en un `ArrayList` compartido.
- Métodos `agregarPedido()` y `retirarPedido()` protegidos con `synchronized`.
- Validación para evitar el registro de pedidos con identificadores repetidos.
- Clase `Repartidor` que implementa la interfaz `Runnable`.
- Simulación de seis pedidos registrados antes de iniciar tres repartidores.
- Retiro de pedidos desde una misma zona de carga, sin asignar una lista individual a cada repartidor.
- Cambio de estado a `EN_REPARTO` al retirar un pedido y a `ENTREGADO` al finalizar la entrega.
- Uso de `Thread.sleep()` con pausas aleatorias para simular las entregas fuera del bloqueo de la zona de carga.
- Uso de `ExecutorService` para ejecutar los tres repartidores en paralelo.
- Uso de `shutdown()` y `awaitTermination()` para esperar que todos los repartidores finalicen.
- Manejo de `InterruptedException` y restauración de la interrupción del hilo.
- Mensajes por repartidor y resumen final para comprobar el estado de los pedidos.
- Verificación de que todos los pedidos estén entregados antes de mostrar el mensaje de éxito.

## Cálculos de tiempo

Se mantienen los cálculos de las semanas anteriores:
- **Comida:** 15 minutos base más 2 minutos por kilómetro, redondeado a entero.
- **Encomienda:** 20 minutos base más 1,5 minutos por kilómetro, redondeado a entero.
- **Express:** 10 minutos base y 5 minutos adicionales cuando la distancia supera los 5 kilómetros.

En esta simulación, la espera de cada entrega es de **entre 1 y 3 segundos**. Estas pausas son independientes del tiempo estimado calculado por cada tipo de pedido.

## Estructura

```text
SpeedFast S5/
├── src/
│   ├── model/
│   │   ├── Pedido.java
│   │   ├── PedidoComida.java
│   │   ├── PedidoEncomienda.java
│   │   ├── PedidoExpress.java
│   │   ├── EstadoPedido.java
│   │   ├── ZonaDeCarga.java
│   │   └── Repartidor.java
│   ├── interfaces/
│   │   ├── Despachable.java
│   │   ├── Cancelable.java
│   │   └── Rastreable.java
│   └── ui/
│       └── Main.java
├── SpeedFast.iml
└── README.md
```

## Ejecución

Los tres repartidores retiran pedidos de la misma zona de carga. Cuando no quedan pedidos disponible finalizan su trabajo. El orden de los mensajes y la cantidad de pedidos que entrega cada repartidor pueden variar entre ejecuciones.
Al finalizar, si los seis pedidos tienen estado `ENTREGADO`, se muestra el mensaje:
```text
Todos los pedidos han sido entregados correctamente
```