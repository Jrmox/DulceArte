-- =========================================================
-- 1. Crear esquema si no existe
-- =========================================================
CREATE SCHEMA IF NOT EXISTS datos;

-- =========================================================
-- 2. Borrar tablas si existen (en orden de detalle a maestro)
-- =========================================================
DROP TABLE IF EXISTS datos.pagos CASCADE;
DROP TABLE IF EXISTS datos.pedido_productos CASCADE;
DROP TABLE IF EXISTS datos.pedidos CASCADE;
DROP TABLE IF EXISTS datos.clientes CASCADE;

-- =========================================================
-- 3. Tabla MAESTRO: CLIENTES
-- =========================================================
CREATE TABLE datos.clientes (
    id_cliente        SERIAL PRIMARY KEY,
    nombre            VARCHAR(150) NOT NULL,
    direccion         VARCHAR(400),
    telefono          VARCHAR(30),
    email             VARCHAR(200),
    fecha_ingreso     DATE NOT NULL,
    persona_registra  VARCHAR(100) NOT NULL,
    estado            INT NOT NULL DEFAULT 1
);

-- Índice opcional para búsqueda por nombre
CREATE INDEX idx_clientes_nombre
    ON datos.clientes (nombre);

-- =========================================================
-- 4. Tabla MAESTRO: PEDIDOS (detalle de CLIENTES)
-- =========================================================
CREATE TABLE datos.pedidos (
    id_pedido           SERIAL PRIMARY KEY,
    id_cliente          INT NOT NULL,
    fecha_pedido        DATE NOT NULL,
    fecha_entrega_prog  DATE NOT NULL,
    hora_entrega_prog   TIME,
    direccion_entrega   VARCHAR(255),
    fecha_registro      TIMESTAMP NOT NULL DEFAULT NOW(),
    monto_total         DECIMAL(10,2) NOT NULL DEFAULT 0,
    monto_pagado        DECIMAL(10,2) NOT NULL DEFAULT 0,
    estado_pedido       VARCHAR(1) NOT NULL DEFAULT 'P',
        -- PENDIENTE, EN_PROCESO, LISTO, ENTREGADO, CANCELADO
    observaciones       VARCHAR(500),
    usuario_registra    VARCHAR(100) NOT NULL,
    fecha_entrega_real  DATE,
    hora_entrega_real   TIME,
    fecha_actualizacion TIMESTAMP,
    CONSTRAINT fk_pedidos_cliente
        FOREIGN KEY (id_cliente)
        REFERENCES datos.clientes(id_cliente)
        ON UPDATE CASCADE
        ON DELETE RESTRICT
);

-- Índices útiles
CREATE INDEX idx_pedidos_cliente
    ON datos.pedidos (id_cliente);

CREATE INDEX idx_pedidos_fecha_entrega
    ON datos.pedidos (fecha_entrega_prog);

CREATE INDEX idx_pedidos_estado
    ON datos.pedidos (estado_pedido);

-- =========================================================
-- 5. Tabla DETALLE: PEDIDO_PRODUCTOS (detalle de PEDIDOS)
-- =========================================================
CREATE TABLE datos.pedido_productos (
    id_pedido_producto  SERIAL PRIMARY KEY,
    id_pedido           INT NOT NULL,
    tipo_producto       VARCHAR(1) NOT NULL,   -- PASTEL, GALLETAS, etc.
    cantidad            INT NOT NULL,
    peso_pastel_kg      NUMERIC(5,2),
    forma_pastel        VARCHAR(50),
    niveles_pastel      INT,
    descripcion_producto VARCHAR(500),
    notas_cliente       VARCHAR(500),
    resultado_pedido    VARCHAR(100),          -- SATISFACTORIO, RECLAMO, etc.
    precio_unitario     DECIMAL(10,2) NOT NULL DEFAULT 0,
    precio_total        DECIMAL(10,2) NOT NULL DEFAULT 0,
    fecha_creacion      TIMESTAMP NOT NULL DEFAULT NOW(),
    fecha_actualizacion TIMESTAMP,
    CONSTRAINT fk_pedidoproductos_pedido
        FOREIGN KEY (id_pedido)
        REFERENCES datos.pedidos(id_pedido)
        ON UPDATE CASCADE
        ON DELETE CASCADE  -- si se borra el pedido, se borran sus productos
);

CREATE INDEX idx_pedido_productos_pedido
    ON datos.pedido_productos (id_pedido);

-- =========================================================
-- 6. Tabla DETALLE: PAGOS (detalle de PEDIDOS)
-- =========================================================
CREATE TABLE datos.pagos (
    id_pago          SERIAL PRIMARY KEY,
    id_pedido        INT NOT NULL,
    fecha_pago       DATE NOT NULL,
    monto_pagado     DECIMAL(10,2) NOT NULL,
    metodo_pago      VARCHAR(50),              -- EFECTIVO, TARJETA, TRANSFERENCIA...
    referencia_pago  VARCHAR(100),
    usuario_registra VARCHAR(100) NOT NULL,
    fecha_registro   TIMESTAMP NOT NULL DEFAULT NOW(),
    observaciones    VARCHAR(255),
    CONSTRAINT fk_pagos_pedido
        FOREIGN KEY (id_pedido)
        REFERENCES datos.pedidos(id_pedido)
        ON UPDATE CASCADE
        ON DELETE RESTRICT   -- puedes cambiar a CASCADE si quieres borrar pagos al borrar pedidos
);

CREATE INDEX idx_pagos_pedido
    ON datos.pagos (id_pedido);

-- =========================================================
-- 7. Comentarios opcionales (documentación dentro de la BD)
-- =========================================================
COMMENT ON SCHEMA datos IS 'Esquema principal de la aplicación Dulce Arte';

COMMENT ON TABLE datos.clientes IS 'Maestro de clientes de la pastelería Dulce Arte';
COMMENT ON TABLE datos.pedidos IS 'Pedidos de clientes (encabezado)';
COMMENT ON TABLE datos.pedido_productos IS 'Productos incluidos en cada pedido (detalle)';
COMMENT ON TABLE datos.pagos IS 'Pagos asociados a cada pedido';

COMMENT ON COLUMN datos.pedidos.estado_pedido IS 'Estado del pedido: PENDIENTE, EN_PROCESO, LISTO, ENTREGADO, CANCELADO';
