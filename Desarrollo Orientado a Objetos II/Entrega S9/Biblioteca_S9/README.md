# Biblioteca Escolar - Semana 9

Esta entrega utiliza como base la organización de SpeedFast Semana 8. Conserva los paquetes `controlador`, `dao`, `interfaces`, `main`, `model` y `vista`, la configuración Maven y la gestión con ventanas Swing, tablas y listas desplegables. Adapta el dominio al sistema de biblioteca escolar e incorpora autenticación por roles, CRUD de libros, estudiantes y categorías, préstamos, devoluciones y reportes mediante JDBC y MySQL.

## Estructura del proyecto

```text
Biblioteca_S9/
├── src/
│   ├── controlador/
│   │   ├── ClaveSegura.java
│   │   ├── ControladorAutenticacion.java
│   │   └── ControladorBiblioteca.java
│   ├── dao/
│   │   ├── DatabaseConnection.java
│   │   ├── CategoriaDAO.java
│   │   ├── EstudianteDAO.java
│   │   ├── LibroDAO.java
│   │   ├── PrestamoDAO.java
│   │   └── UsuarioDAO.java
│   ├── interfaces/
│   │   ├── CRUD.java
│   │   └── Identificable.java
│   ├── main/
│   │   └── Main.java
│   ├── model/
│   │   ├── Bibliotecario.java
│   │   ├── Categoria.java
│   │   ├── Estudiante.java
│   │   ├── Libro.java
│   │   ├── Persona.java
│   │   ├── Prestamo.java
│   │   └── Usuario.java
│   └── vista/
│       ├── Componentes.java
│       ├── VentanaAutenticacion.java
│       ├── VentanaGestionCRUD.java
│       ├── VentanaPrestamos.java
│       ├── VentanaPrincipal.java
│       └── VentanaReportes.java
├── dist/
│   └── Biblioteca_S9.jar
├── pruebas/
│   ├── PruebasConcurrencia.java
│   ├── PruebasIntegracion.java
│   └── PruebasInterfaz.java
├── .gitignore
├── database.properties
├── pom.xml
├── README.md
├── Script_crea_tablas_biblioteca.sql
└── Script_poblado_tablas_biblioteca.sql
```

- **controlador:** valida los datos, autentica las cuentas y coordina las operaciones. Los permisos se verifican en el controlador además de organizar los accesos de la interfaz. Las contraseñas se protegen con PBKDF2 y una sal individual.
- **dao:** gestiona la conexión y la persistencia mediante JDBC, PreparedStatement y ResultSet. DatabaseConnection implementa Singleton con una conexión compartida cuyo uso se sincroniza. El registro de estudiante y su cuenta se realiza en una transacción.
- **interfaces:** define el contrato Identificable y las operaciones CRUD utilizadas por las entidades del catálogo.
- **main:** contiene el punto de entrada de la aplicación.
- **model:** representa usuarios, estudiantes, bibliotecarios, categorías, libros y préstamos. Persona y Usuario son clases abstractas; Estudiante y Bibliotecario aplican herencia y resuelven polimórficamente los permisos.
- **vista:** organiza autenticación, catálogo, estudiantes, categorías, préstamos, devoluciones y reportes. El bibliotecario accede a la gestión completa y el estudiante consulta el catálogo y opera sobre sus propios préstamos. Los procesos de base de datos se ejecutan con SwingWorker; las actualizaciones de la interfaz ocurren en el hilo de eventos de Swing.
- **préstamos y devoluciones:** registran automáticamente un vencimiento de 14 días y mantienen el historial en la tabla prestamos. La transacción agrupa el registro del préstamo y la actualización del stock; los métodos synchronized y SELECT FOR UPDATE previenen inconsistencias. La devolución restituye una sola unidad y deja constancia del atraso. El inventario no admite un total menor que los ejemplares actualmente prestados.
- **reportes:** presenta los libros más prestados, el historial por estudiante y los préstamos activos. Los reportes individuales del estudiante se limitan a su propia cuenta.
- **pom.xml:** conserva Maven y MySQL Connector/J 9.5.0, con Java 17 y empaquetado del ejecutable y sus dependencias.
- **database.properties:** contiene la configuración de la conexión a biblioteca_db. La conexión también admite las variables BIBLIOTECA_DB_URL, BIBLIOTECA_DB_USER y BIBLIOTECA_DB_PASSWORD.
- **Script_crea_tablas_biblioteca.sql:** define biblioteca_db, sus cinco tablas, claves foráneas, restricciones de inventario, índices y la cuenta de conexión con permisos de consulta y escritura.
- **Script_poblado_tablas_biblioteca.sql:** incorpora cuentas, estudiantes, categorías, libros y préstamos de ejemplo, incluyendo un préstamo vencido y una devolución con atraso.
- **dist:** contiene Biblioteca_S9.jar, con la clase principal y las dependencias incorporadas.
- **pruebas:** contiene comprobaciones de CRUD, autenticación, permisos, rollback, reportes, concurrencia entre hilos y procesos, y respuesta de la interfaz durante una espera SQL.
