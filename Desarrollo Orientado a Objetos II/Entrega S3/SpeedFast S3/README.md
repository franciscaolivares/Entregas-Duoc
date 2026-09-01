# SpeedFast

Actividad sumativa de la Semana 3 de Desarrollo Orientado a Objetos II.

Esta entrega continúa el proyecto realizado durante las semanas anteriores. Se mantiene la jerarquía de pedidos, la asignación de repartidores y el cálculo del tiempo estimado. Además, se incorporan interfaces para despachar, cancelar y consultar el historial de cada pedido.

## Conceptos aplicados

- Clase abstracta `Pedido` con atributos comunes.
- Método implementado `mostrarResumen()`.
- Método abstracto `calcularTiempoEntrega()`.
- Herencia mediante las clases `PedidoComida`, `PedidoEncomienda` y `PedidoExpress`.
- Sobrescritura de métodos con una fórmula distinta para cada pedido.
- Polimorfismo mediante un arreglo de referencias de tipo `Pedido`.
- Interfaces `Despachable`, `Cancelable` y `Rastreable`.
- Reserva, despacho, cancelación y seguimiento mediante un `ArrayList`.
- Uso de interfaces en los tres tipos de pedido.
- Simulación de tres casos diferenciados desde la clase `Main`.
- Reutilización de la lógica desarrollada en las semanas anteriores.

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
│   │   └── PedidoExpress.java
│   ├── interfaces/
│   │   ├── Despachable.java
│   │   ├── Cancelable.java
│   │   └── Rastreable.java
│   └── ui/
│       └── Main.java
├── SpeedFast.iml
└── README.md
```
