# SpeedFast - Semana 8

Esta entrega continúa el código de SpeedFast Semana 7. Conserva la jerarquía `Pedido`, las interfaces, `ZonaDeCarga`, `Repartidor`, el controlador y las ventanas Swing. Completa las operaciones CRUD de repartidores, pedidos y entregas mediante JDBC y MySQL, incorporando validaciones, manejo de errores y una ventana de gestión con tablas y listas desplegables.

## Estructura del proyecto

```text
SpeedFast_S8/
├── src/
│   ├── controlador/
│   │   └── ControladorPedidos.java
│   ├── dao/
│   │   ├── ConexionBD.java
│   │   ├── ConexionDB.java
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
│       ├── VentanaGestionCRUD.java
│       ├── VentanaListaPedidos.java
│       ├── VentanaPrincipal.java
│       ├── VentanaRegistroPedido.java
│       └── VentanaRepartidores.java
├── .gitignore
├── pom.xml
├── README.md
└── schema.sql
```

- **controlador:** valida los datos y coordina el registro, la consulta y las entregas.
- **dao:** gestiona la conexión y las operaciones de creación, consulta, actualización y eliminación con MySQL mediante JDBC, PreparedStatement y ResultSet.
- **interfaces:** conserva los contratos de cancelación, despacho y seguimiento.
- **main:** contiene el punto de entrada de la aplicación.
- **model:** representa los pedidos, repartidores, entregas y la zona de carga. La entidad Entrega incorpora identificador, fecha y hora.
- **vista:** conserva las ventanas gráficas anteriores y agrega VentanaGestionCRUD para registrar, consultar, editar y eliminar repartidores, pedidos y entregas, con validaciones y actualización de tablas y combos.
- **pom.xml:** configura Maven y la dependencia del conector MySQL.
- **schema.sql:** conserva la base de datos `speedfast_db`, sus tablas y relaciones de la Semana 7. La fecha y hora de cada entrega se almacenan juntas en `fecha_inicio`.



