# SpeedFast

Actividad formativa de la Semana 4 de Desarrollo Orientado a Objetos II.

Esta entrega continúa el proyecto realizado durante las semanas anteriores. Se mantiene la jerarquía de pedidos y las interfaces creadas en la Semana 3. Además, se incorpora programación concurrente para simular que varios repartidores realizan entregas al mismo tiempo.

## Conceptos aplicados

- Clase abstracta `Pedido` con atributos comunes.
- Método implementado `mostrarResumen()`.
- Método abstracto `calcularTiempoEntrega()`.
- Herencia mediante las clases `PedidoComida`, `PedidoEncomienda` y `PedidoExpress`.
- Sobrescritura de métodos con una fórmula distinta para cada pedido.
- Polimorfismo mediante referencias de tipo `Pedido` en la lista de cada repartidor.
- Interfaces `Despachable`, `Cancelable` y `Rastreable`.
- Reserva, despacho, cancelación y seguimiento mediante un `ArrayList`.
- Uso de interfaces en los tres tipos de pedido.
- Simulación de seis pedidos distribuidos entre tres repartidores.
- Reutilización de la lógica desarrollada en las semanas anteriores.
- Clase `Repartidor` que implementa la interfaz `Runnable`.
- Una lista de pedidos asignados para cada repartidor.
- Uso de `Thread.sleep()` con pausas aleatorias para simular las entregas.
- Creación de tres repartidores con dos pedidos cada uno.
- Uso de `ExecutorService` para ejecutar las entregas en paralelo.
- Uso de `shutdown()` y `awaitTermination()` para esperar que todos los repartidores finalicen.
- Manejo de `InterruptedException` durante la ejecución de los hilos.

## Cálculos de tiempo

- Comida: 15 minutos base más 2 minutos por kilómetro.
- Encomienda: 20 minutos base más 1,5 minutos por kilómetro, ajustado a entero.
- Express: 10 minutos base y 5 minutos adicionales cuando la distancia supera los 5 kilómetros.

## Estructura

```text
SpeedFast/
├── src/
│   ├── model/
│   │   ├── Pedido.java
│   │   ├── PedidoComida.java
│   │   ├── PedidoEncomienda.java
│   │   ├── PedidoExpress.java
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
