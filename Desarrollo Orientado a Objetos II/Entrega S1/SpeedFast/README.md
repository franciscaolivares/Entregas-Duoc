# SpeedFast

Actividad formativa de la Semana 1 de Desarrollo Orientado a Objetos II.

El proyecto representa un sistema de asignación de repartidores para tres tipos de pedidos:

- Pedido de comida.
- Pedido de encomienda.
- Pedido de compra express.

## Conceptos aplicados

- Encapsulamiento mediante atributos privados.
- Herencia entre la clase `Pedido` y sus subclases.
- Sobrescritura del método `asignarRepartidor()`.
- Sobrecarga mediante `asignarRepartidor(String nombreRepartidor)`.
- Polimorfismo mediante un arreglo de referencias de tipo `Pedido`.

## Estructura

```text
SpeedFast/
├── src/
│   ├── model/
│   │   ├── Pedido.java
│   │   ├── PedidoComida.java
│   │   ├── PedidoEncomienda.java
│   │   └── PedidoExpress.java
│   └── ui/
│       └── Main.java
├── .idea/
├── SpeedFast.iml
└── README.md
```