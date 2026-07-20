# LlanquihueTourApp

## Autor del proyecto

**Nombre completo:** Francisca Belén Olivares Cortés  
**Sección:**   
**Carrera:** Analista Programador  
**Sede:** Online

---

# Descripción

Este proyecto corresponde a la **Evaluación Final Transversal – Semana 9** de la asignatura **Desarrollo Orientado a Objetos I**.

El sistema continúa desarrollando la aplicación **Llanquihue Tour**, incorporando y aplicando conceptos de Programación Orientada a Objetos como encapsulamiento, composición, herencia, interfaces, polimorfismo, colecciones dinámicas, validación mediante excepciones personalizadas y diferenciación de objetos mediante el operador `instanceof`.

Además, se utiliza una interfaz gráfica básica mediante **JOptionPane**, permitiendo registrar distintas entidades de la agencia turística, cargar información desde un archivo `.txt`, gestionar clientes, servicios turísticos y reservas, además de visualizar, buscar y filtrar información almacenada.

---

# Estructura del proyecto

```text
src
├── data
│   ├── GestorDatos.java
│   ├── GestorServicios.java
│   ├── GestorEntidades.java
│   └── GestorReservas.java
│
├── interfaces
│   └── Registrable.java
│
├── model
│   ├── Persona.java
│   ├── Cliente.java
│   ├── Guia.java
│   ├── Proveedor.java
│   ├── Direccion.java
│   ├── Rut.java
│   ├── ServicioTuristico.java
│   ├── RutaGastronomica.java
│   ├── PaseoLacustre.java
│   ├── ExcursionCultural.java
│   ├── Vehiculo.java
│   ├── ColaboradorExterno.java
│   ├── Tour.java
│   └── Reserva.java
│
├── ui
│   └── Main.java
│
└── utils
    └── RutInvalidoException.java

resources
└── tours.txt
````

---

# Descripción de las clases

### Registrable

Interfaz que define el método `mostrarResumen()`, utilizado por distintas entidades registrables del sistema.

### Persona

Clase base que contiene atributos comunes como nombre, teléfono, RUT y dirección. Es utilizada mediante herencia por otras clases del sistema.

### Cliente

Clase que hereda de Persona y representa a los clientes de la agencia. Incorpora información adicional como correo electrónico.

### Guia

Clase que hereda de Persona y representa a un guía turístico. Permite aplicar herencia, sobrescritura y sobrecarga de constructores.

### Proveedor

Clase que hereda de Persona y representa proveedores asociados a distintos tipos de servicios turísticos.

### Direccion

Clase utilizada para representar la dirección de una persona mediante composición.

### Rut

Clase encargada de almacenar y validar el formato de un RUT.

### RutInvalidoException

Excepción personalizada utilizada para controlar errores cuando el formato del RUT ingresado no es válido.

### ServicioTuristico

Superclase que contiene los atributos comunes de los servicios turísticos.

### RutaGastronomica

Clase que hereda de ServicioTuristico y representa rutas gastronómicas.

### PaseoLacustre

Clase que hereda de ServicioTuristico y representa paseos lacustres.

### ExcursionCultural

Clase que hereda de ServicioTuristico y representa excursiones culturales.

### Vehiculo

Representa un vehículo utilizado por la agencia e implementa la interfaz Registrable.

### ColaboradorExterno

Representa un colaborador externo asociado a la agencia e implementa la interfaz Registrable.

### Tour

Representa un tour de la agencia y utiliza composición al contener un objeto de tipo Guia.

### Reserva

Representa una reserva realizada en el sistema, relacionando un Cliente, un ServicioTuristico y un Guia.

### GestorDatos

Clase encargada de leer la información almacenada en el archivo `tours.txt` y convertir los datos en objetos Tour.

### GestorServicios

Clase encargada de crear y administrar los distintos servicios turísticos disponibles.

### GestorEntidades

Administra una colección `ArrayList<Registrable>`, permitiendo almacenar y recorrer distintas entidades del sistema mediante polimorfismo y `instanceof`.

También permite obtener clientes y guías registrados para utilizarlos en otras funcionalidades del sistema.

### GestorReservas

Administra una colección de objetos Reserva, permitiendo agregar, mostrar, buscar y filtrar reservas.

### Main

Clase principal que ejecuta el sistema mediante una interfaz gráfica desarrollada con JOptionPane.

Permite registrar entidades, visualizar información, cargar tours desde archivo y gestionar reservas.

---

# Conceptos aplicados

* Encapsulamiento.
* Getters y Setters.
* Constructores.
* Sobrecarga de constructores.
* Método `toString()`.
* Herencia (`extends`).
* Composición entre clases.
* Polimorfismo.
* Interfaces (`implements`).
* Sobrescritura de métodos (`@Override`).
* Colecciones dinámicas (`ArrayList`).
* Recorrido de colecciones.
* Búsqueda y filtrado de objetos.
* Operador `instanceof`.
* Excepciones personalizadas.
* Validación del formato del RUT.
* Lectura de datos desde un archivo `.txt`.
* Interfaz gráfica con `JOptionPane`.
* Organización modular mediante paquetes.

---

# Instrucciones de ejecución

1. Clonar el repositorio desde GitHub.
2. Abrir el proyecto `LlanquihueTourApp` en IntelliJ IDEA.
3. Verificar que el directorio de trabajo corresponda a la carpeta principal `LlanquihueTourApp`.
4. Ejecutar la clase `Main.java` ubicada en el paquete `ui`.
5. Utilizar las distintas opciones disponibles en el menú.
6. Registrar entidades como clientes y guías.
7. Visualizar los tours cargados desde el archivo `resources/tours.txt`.
8. Registrar, mostrar, buscar y filtrar reservas desde las opciones correspondientes del sistema.

---

# Repositorio

**GitHub:** *(https://github.com/franciscaolivares/Entregas-Duoc/tree/main/Desarrollo%20Orientado%20a%20Objetos%20I)*

---

# Fecha de entrega: 19 julio 

**Semana 9 – Evaluación Final Transversal – Desarrollo Orientado a Objetos I**


