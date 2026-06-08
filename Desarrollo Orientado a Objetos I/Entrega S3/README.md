👤 Autor del proyecto
Nombre completo: Francisca Belén Olivares Cortés
Sección: [Escribe aquí tu sección]
Carrera: Analista programadoer
Sede: Online


Descripción
Este proyecto corresponde a la Sumativa I de la asignatura Desarrollo Orientado a Objetos I. Se trata de un sistema orientado a objetos desarrollado en Java, cuyo objetivo es modelar y gestionar entidades representativas de un dominio real, aplicando los principios de encapsulamiento, composición, herencia, polimorfismo e interfaces. El proyecto fue desarrollado a partir de un caso contextualizado, abordando problemáticas reales y proponiendo una solución estructurada, modular y reutilizable. 

Estructura del proyecto

El proyecto está organizado en tres paquetes principales:

  ```text
src
├── app
│   └── Main.java
├── model
│   ├── Direccion.java
│   ├── Empleado.java
│   ├── Persona.java
│   └── Rut.java
└── util
    └── RutInvalidoException.java
```
Descripción de las clases
- Persona: clase base que contiene nombre, teléfono, RUT y dirección.
- Empleado: clase que hereda de Persona y agrega cargo y sueldo.
- Direccion: clase utilizada por composicion dentro de Persona.
- Rut: clase que válida el formato del RUT chileno.
- RutInvalidoException: excepción personalizada para manejar errores de RUT.
- Main: clase principal donde se crean objetos y se muestran los datos por consola.

Conceptos aplicados
- Encapsulamiento con atributos private.
- Metodos get y set.
- Constructores.
- Metodo toString().
- Composicion entre Persona, Rut y Direccion.
- Herencia entre Empleado y Persona.
- Manejo de excepciones personalizadas.
- Organizacion en paquetes.

⚙️ Instrucciones para clonar y ejecutar el proyecto

Clona el repositorio desde GitHub:
git clone ""
Abre el proyecto en IntelliJ IDEA.

Verifica que los archivos .txt estén correctamente ubicados.

Ejecuta el archivo Main.java desde el paquete app.

Sigue las instrucciones en consola o en la interfaz gráfica (si corresponde).

Repositorio GitHub: [Pega aquí el enlace al repositorio] Fecha de entrega: [08/06/2026]