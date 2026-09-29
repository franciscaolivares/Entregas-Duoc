CREATE DATABASE IF NOT EXISTS speedfast_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE speedfast_db;

CREATE TABLE IF NOT EXISTS pedidos (
    id_pedido INT PRIMARY KEY,
    direccion_entrega VARCHAR(200) NOT NULL,
    tipo_pedido VARCHAR(20) NOT NULL,
    distancia_km DOUBLE NOT NULL,
    estado VARCHAR(20) NOT NULL DEFAULT 'PENDIENTE',
    CONSTRAINT chk_tipo CHECK (tipo_pedido IN ('Comida', 'Encomienda', 'Express')),
    CONSTRAINT chk_distancia CHECK (distancia_km > 0),
    CONSTRAINT chk_estado CHECK (estado IN ('PENDIENTE', 'EN_REPARTO', 'ENTREGADO'))
);

CREATE TABLE IF NOT EXISTS repartidores (
    id_repartidor INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS entregas (
    id_entrega INT AUTO_INCREMENT PRIMARY KEY,
    id_pedido INT NOT NULL,
    id_repartidor INT NOT NULL,
    fecha_inicio TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    fecha_fin TIMESTAMP NULL,
    estado VARCHAR(20) NOT NULL DEFAULT 'EN_REPARTO',
    CONSTRAINT fk_entrega_pedido FOREIGN KEY (id_pedido) REFERENCES pedidos(id_pedido),
    CONSTRAINT fk_entrega_repartidor FOREIGN KEY (id_repartidor) REFERENCES repartidores(id_repartidor),
    CONSTRAINT chk_estado_entrega CHECK (estado IN ('EN_REPARTO', 'ENTREGADO'))
);
