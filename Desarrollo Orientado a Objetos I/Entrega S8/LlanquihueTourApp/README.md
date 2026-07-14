# LlanquihueTourApp

## Autor del proyecto

**Nombre completo:** Francisca Belén Olivares Cortés  
**Sección:**   
**Carrera:** Analista Programador  
**Sede:** Online

---

# Descripción

Este proyecto corresponde a la **Actividad Formativa de la Semana 8** de la asignatura **Desarrollo Orientado a Objetos I**.

El sistema continúa desarrollando la aplicación **Llanquihue Tour**, incorporando nuevos conceptos de Programación Orientada a Objetos como interfaces, polimorfismo, colecciones dinámicas y diferenciación de objetos mediante el operador `instanceof`.

Además, se implementó una interfaz gráfica básica utilizando **JOptionPane**, permitiendo registrar distintas entidades de la agencia turística y visualizar la información almacenada.

---

# Estructura del proyecto

```
src
├── data
│   ├── GestorDatos.java
│   ├── GestorServicios.java
│   └── GestorEntidades.java
│
├── model
│   ├── Registrable.java
│   ├── ServicioTuristico.java
│   ├── RutaGastronomica.java
│   ├── PaseoLacustre.java
│   ├── ExcursionCultural.java
│   ├── Guia.java
│   ├── Vehiculo.java
│   ├── ColaboradorExterno.java
│   └── Tour.java
│
└── ui
    └── Main.java
```

---

# Descripción de las clases

### Registrable
Interfaz que define el método `mostrarResumen()`, utilizado por las entidades registrables del sistema.

### ServicioTuristico
Superclase que contiene los atributos comunes de los servicios turísticos.

### RutaGastronomica
Clase que hereda de ServicioTuristico y representa rutas gastronómicas.

### PaseoLacustre
Clase que hereda de ServicioTuristico y representa paseos lacustres.

### ExcursionCultural
Clase que hereda de ServicioTuristico y representa excursiones culturales.

### Guia
Representa un guía turístico que implementa la interfaz Registrable.

### Vehiculo
Representa un vehículo de la agencia e implementa la interfaz Registrable.

### ColaboradorExterno
Representa un colaborador externo e implementa la interfaz Registrable.

### GestorServicios
Clase encargada de crear y mostrar los servicios turísticos desarrollados en la Semana 7.

### GestorEntidades
Administra una colección `ArrayList<Registrable>`, permitiendo almacenar y recorrer distintas entidades del sistema mediante polimorfismo y `instanceof`.

### Main
Clase principal que ejecuta el sistema mediante una interfaz gráfica desarrollada con JOptionPane.

---

# Conceptos aplicados

- Encapsulamiento.
- Getters y Setters.
- Constructores.
- Método `toString()`.
- Herencia (`extends`).
- Polimorfismo.
- Interfaces (`implements`).
- Sobrescritura de métodos (`@Override`).
- Colecciones dinámicas (`ArrayList`).
- Operador `instanceof`.
- Interfaz gráfica con `JOptionPane`.
- Organización modular mediante paquetes.

---

# Instrucciones de ejecución

1. Clonar el repositorio desde GitHub.
2. Abrir el proyecto en IntelliJ IDEA.
3. Ejecutar la clase `Main.java` ubicada en el paquete `ui`.
4. Registrar entidades desde la interfaz gráfica.
5. Visualizar las entidades registradas utilizando la opción correspondiente del menú.

---

# Repositorio

**GitHub:** *(Pega aquí el enlace de tu repositorio)*

---

# Fecha de entrega

**Semana 8 – Desarrollo Orientado a Objetos I**