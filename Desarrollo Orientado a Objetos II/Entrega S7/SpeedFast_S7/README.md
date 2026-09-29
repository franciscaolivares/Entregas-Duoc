# SpeedFast - Semana 7

Esta entrega continúa el código de SpeedFast Semana 6. Conserva la jerarquía `Pedido`, las interfaces, `ZonaDeCarga`, `Repartidor` y las ventanas Swing. Agrega persistencia MySQL con JDBC y las clases `ConexionBD`, `PedidoDAO`, `RepartidorDAO` y `EntregaDAO`.

## Estructura del proyecto

```text
SpeedFast_S7/
├── src/
│   ├── controlador/
│   │   └── ControladorPedidos.java
│   ├── dao/
│   │   ├── ConexionBD.java
│   │   ├── EntregaDAO.java
│   │   ├── PedidoDAO.java
│   │   └── RepartidorDAO.java
│   ├── interfaces/
│   │   ├── Cancelable.java
│   │   ├── Despachable.java
│   │   └── Rastreable.java
│   ├── main/
│   │   └── Main.java
│   ├── model/
│   │   ├── Entrega.java
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
│       ├── VentanaRegistroPedido.java
│       └── VentanaRepartidores.java
├── .gitignore
├── pom.xml
├── README.md
├── schema.sql
└── SpeedFast_S7.iml
```

- **controlador:** valida los datos y coordina el registro, la consulta y las entregas.
- **dao:** gestiona la conexión y las operaciones con MySQL mediante JDBC.
- **interfaces:** conserva los contratos de cancelación, despacho y seguimiento.
- **main:** contiene el punto de entrada de la aplicación.
- **model:** representa los pedidos, repartidores, entregas y la zona de carga.
- **vista:** contiene las ventanas gráficas desarrolladas con Java Swing.
- **pom.xml:** configura Maven y la dependencia del conector MySQL.
- **schema.sql:** crea la base de datos `speedfast_db`, sus tablas y relaciones.



