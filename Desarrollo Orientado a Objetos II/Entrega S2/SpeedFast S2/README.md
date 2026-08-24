# SpeedFast

Actividad formativa de la Semana 2 de Desarrollo Orientado a Objetos II.

Esta entrega continúa el proyecto realizado durante la Semana 1. Se mantuvo la lógica de asignación de repartidores y se agregó el cálculo del tiempo estimado para cada tipo de pedido.

## Conceptos aplicados

- Clase abstracta `Pedido` con atributos comunes.
- Método implementado `mostrarResumen()`.
- Método abstracto `calcularTiempoEntrega()`.
- Herencia mediante las clases `PedidoComida`, `PedidoEncomienda` y `PedidoExpress`.
- Sobrescritura de métodos con una fórmula distinta para cada pedido.
- Polimorfismo mediante un arreglo de referencias de tipo `Pedido`.
- Reutilización de la lógica desarrollada en la Semana 1.

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
│   └── ui/
│       └── Main.java
├── SpeedFast.iml
└── README.md
```
