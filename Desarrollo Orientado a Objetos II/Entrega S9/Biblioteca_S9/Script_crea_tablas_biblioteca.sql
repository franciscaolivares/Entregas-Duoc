CREATE DATABASE IF NOT EXISTS biblioteca_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE biblioteca_db;
CREATE TABLE IF NOT EXISTS usuarios (
 id INT AUTO_INCREMENT PRIMARY KEY, nombre VARCHAR(100) NOT NULL, login VARCHAR(50) NOT NULL UNIQUE,
 clave_hash VARCHAR(255) NOT NULL, rol ENUM('BIBLIOTECARIO','ESTUDIANTE') NOT NULL
) ENGINE=InnoDB;
CREATE TABLE IF NOT EXISTS estudiantes (
 id INT AUTO_INCREMENT PRIMARY KEY, usuario_id INT NOT NULL UNIQUE, rut VARCHAR(15) NOT NULL UNIQUE,
 curso VARCHAR(50) NOT NULL, correo VARCHAR(100) NOT NULL,
 FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE RESTRICT
) ENGINE=InnoDB;
CREATE TABLE IF NOT EXISTS categorias (id INT AUTO_INCREMENT PRIMARY KEY,nombre VARCHAR(80) NOT NULL UNIQUE) ENGINE=InnoDB;
CREATE TABLE IF NOT EXISTS libros (
 id INT AUTO_INCREMENT PRIMARY KEY, titulo VARCHAR(150) NOT NULL, autor VARCHAR(100) NOT NULL,
 editorial VARCHAR(100) NOT NULL,isbn VARCHAR(20) NOT NULL UNIQUE,categoria_id INT NOT NULL,
 stock INT NOT NULL,stock_total INT NOT NULL,
 CHECK(stock>=0 AND stock<=stock_total),CHECK(stock_total>=0),
 FOREIGN KEY (categoria_id) REFERENCES categorias(id) ON DELETE RESTRICT
) ENGINE=InnoDB;
CREATE TABLE IF NOT EXISTS prestamos (
 id INT AUTO_INCREMENT PRIMARY KEY,libro_id INT NOT NULL,estudiante_id INT NOT NULL,
 fecha_prestamo DATE NOT NULL,fecha_vencimiento DATE NOT NULL,fecha_devolucion DATE NULL,
 atraso BOOLEAN NOT NULL DEFAULT FALSE,
 CHECK(fecha_vencimiento>=fecha_prestamo),CHECK(fecha_devolucion IS NULL OR fecha_devolucion>=fecha_prestamo),
 FOREIGN KEY(libro_id) REFERENCES libros(id) ON DELETE RESTRICT,
 FOREIGN KEY(estudiante_id) REFERENCES estudiantes(id) ON DELETE RESTRICT,
 INDEX idx_prestamos_estudiante(estudiante_id,fecha_devolucion),INDEX idx_prestamos_libro(libro_id,fecha_devolucion)
) ENGINE=InnoDB;
CREATE USER IF NOT EXISTS 'biblioteca_app'@'localhost' IDENTIFIED BY 'Biblioteca2026!';
GRANT SELECT,INSERT,UPDATE,DELETE ON biblioteca_db.* TO 'biblioteca_app'@'localhost';
