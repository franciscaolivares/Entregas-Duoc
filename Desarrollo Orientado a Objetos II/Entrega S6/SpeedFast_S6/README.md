# SpeedFast - Semana 6

Actividad formativa de Desarrollo Orientado a Objetos II. Continúa las clases del proyecto de Semana 5 y agrega una interfaz Java Swing para registrar pedidos, listarlos, asignar repartidores y simular entregas.

## Estructura del proyecto

```text
SpeedFast_S6/
├── src/
│   ├── controlador/
│   │   └── ControladorPedidos.java
│   ├── interfaces/
│   │   ├── Cancelable.java
│   │   ├── Despachable.java
│   │   └── Rastreable.java
│   ├── main/
│   │   └── Main.java
│   ├── model/
│   │   ├── EstadoPedido.java
│   │   ├── Pedido.java
│   │   ├── PedidoComida.java
│   │   ├── PedidoEncomienda.java
│   │   ├── PedidoExpress.java
│   │   ├── Repartidor.java
│   │   └── ZonaDeCarga.java
│   └── vista/
│       ├── VentanaListaPedidos.java
│       ├── VentanaPrincipal.java
│       └── VentanaRegistroPedido.java
├── .gitignore
├── README.md
└── SpeedFast_S6.iml
```

- **controlador:** administra la lista compartida de pedidos, valida los datos y coordina las entregas.
- **interfaces:** conserva los contratos de cancelación, despacho y seguimiento.
- **main:** contiene el punto de entrada de la aplicación.
- **model:** representa los pedidos, sus estados, los repartidores y la zona de carga.
- **vista:** contiene la ventana principal, el formulario de registro y el listado de pedidos, desarrollados con Java Swing.

En esta semana, los datos se almacenan en memoria durante la ejecución del programa, sin conexión a una base de datos.s
