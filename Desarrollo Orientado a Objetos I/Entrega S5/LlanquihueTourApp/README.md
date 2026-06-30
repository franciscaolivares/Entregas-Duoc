# LlanquihueTourApp

## 👤 Autor del proyecto

**Nombre completo:** Francisca Belén Olivares Cortés
**Sección:** []
**Carrera:** Analista Programador
**Sede:** Online

---

# Descripción

Este proyecto corresponde a la actividad formativa de la semana 4, también de la actividad sumativa de la semana 5 y formativa de la semana 6 de la asignatura Desarrollo Orientado a Objetos I.
El objetivo es desarrollar una aplicación en Java capaz de leer información desde un archivo de texto (.txt), crear objetos a partir de esos datos y almacenarlos en una colección dinámica utilizando ArrayList.
La aplicación permite recorrer la colección completa de tours turísticos y aplicar filtros según determinadas condiciones, fortaleciendo el uso de clases, objetos, colecciones, lectura de archivos y organización de proyectos mediante paquetes.
En la semana 6 se incorporó una jerarquía de clases utilizando herencia simple, donde una superclase representa los servicios turísticos generales y las subclases representan distintos tipos de servicios ofrecidos por la agencia.
---

# Estructura del proyecto

```text
LlanquihueTourApp
├── src
│   ├── model
│   │   └── Tour.java
│   │   └── ServicioTuristico.java
│   │   └── RutaGastronomica.java
│   │   └── PaseoLacustre.java
│   │   └── ExcursionCultural.java
│   ├── data
│   │   └── GestorDatos.java
│   └── ui
│       └── Main.java
├── resources
│   └── tours.txt
└── README.md
```

---

# Descripción de las clases

### Tour

Clase que representa un tour turístico. Contiene los atributos:

* nombre
* tipo
* precio

Además incluye constructores, getters, setters y método toString().

### Guia

Clase que representa al guía encargado de un tour. Contiene nombre y teléfono.

### GestorDatos

Clase encargada de leer el archivo tours.txt, separar los datos utilizando split(";"), crear objetos Tour y Guia, y almacenarlos dentro de un ArrayList.

### Main

Clase principal del proyecto. Permite:

* Cargar los datos desde el archivo.
* Mostrar todos los tours registrados.
* Filtrar tours según una condición.
* Mostrar los resultados filtrados por consola.

---

# Conceptos aplicados

* Programación Orientada a Objetos.
* Encapsulamiento mediante atributos privados.
* Métodos getters y setters.
* Constructores.
* Método toString().
* Composición entree Tour y Guia.
* Lectura de archivos de texto (.txt).
* Uso de ArrayList.
* Uso de split(";") para procesar datos.
* Recorrido de colecciones mediante ciclos.
* Filtrado de información.
* Organización de código mediante paquetes.

---

# ⚙️ Instrucciones para clonar y ejecutar el proyecto

### Clonar el repositorio

```bash
git clone https://github.com/franciscaolivares/Entregas-Duoc/tree/main/Desarrollo%20Orientado%20a%20Objetos%20I
```

### Abrir el proyecto

1. Abrir IntelliJ IDEA.
2. Seleccionar "Open".
3. Buscar la carpeta LlanquihueTourApp.
4. Verificar que exista la carpeta resources con el archivo tours.txt.

### Ejecutar la aplicación

1. Abrir el archivo Main.java.
2. Ejecutar el método main().
3. Revisar los resultados mostrados en la consola.

---

# Archivo de datos

El archivo tours.txt contiene información separada por punto y coma (;).

Ejemplo:

```text
Ruta del Lago; lacustre; 18000; Camila Carvajal; 9782637766
Sabores del Sur; gastronómico; 25000; Pedro Díaz; 97283647
Cultura Llanquihue; cultural; 15000; Daniel Muñoz; 973647362
Volcan Osorno; aventura; 30000; Pamela Rojas; 91728364
Mercado Local; gastronomico; 12000; Nestor Hidalgo; 982764527
```

---

**Repositorio GitHub:** [https://github.com/franciscaolivares/Entregas-Duoc/tree/main/Desarrollo%20Orientado%20a%20Objetos%20I]

**Fecha de  1era entrega:** [15/06/2026]
**Fecha de 2da entrega:** [21/06/2026]
