/* =====================================================================
   PlantaProduccion — Esquema de la base de datos (SQL Server)

   Crea la estructura vacía (10 tablas) que usa la API.
   Ejecutalo una vez en SQL Server (por ejemplo desde SSMS) antes de
   levantar el backend.

   Orden de creación: primero los catálogos, luego la orden, y al final
   los detalles (que dependen de la orden).
   ===================================================================== */

-- 1) Crear la base (si no existe)
IF DB_ID('PlantaProduccion') IS NULL
    CREATE DATABASE PlantaProduccion;
GO

USE PlantaProduccion;
GO

/* ============================ CATÁLOGOS ============================ */

CREATE TABLE turno (
    id     INT IDENTITY(1,1) PRIMARY KEY,
    nombre VARCHAR(20) NOT NULL UNIQUE
);

CREATE TABLE maquina (
    id     INT IDENTITY(1,1) PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL UNIQUE,
    tipo   VARCHAR(50) NULL
);

CREATE TABLE operario (
    id     INT IDENTITY(1,1) PRIMARY KEY,
    numero VARCHAR(10)  NULL UNIQUE,
    nombre VARCHAR(100) NOT NULL
);

CREATE TABLE actividad (
    id          INT IDENTITY(1,1) PRIMARY KEY,
    codigo      INT NULL UNIQUE,
    descripcion VARCHAR(100) NOT NULL
);

/* ===================== ORDEN (cabecera / hoja) ===================== */

CREATE TABLE orden_produccion (
    id          INT IDENTITY(1,1) PRIMARY KEY,
    fecha       DATE NOT NULL,
    diseno      VARCHAR(100) NULL,
    op_codigo   VARCHAR(30)  NOT NULL UNIQUE,
    maquina_id  INT NOT NULL,           -- máquina obligatoria
    operario_id INT NULL,               -- operario opcional
    turno_id    INT NULL,               -- turno opcional
    hora_inicio TIME NULL,
    hora_fin    TIME NULL,
    creado_en   DATETIME2 NULL,
    estado      VARCHAR(20) NOT NULL
        CONSTRAINT DF_orden_estado DEFAULT 'EN_PROCESO'
        CONSTRAINT CK_orden_estado CHECK (estado IN ('EN_PROCESO', 'TERMINADA', 'CANCELADA')),
    CONSTRAINT FK_orden_maquina  FOREIGN KEY (maquina_id)  REFERENCES maquina(id),
    CONSTRAINT FK_orden_operario FOREIGN KEY (operario_id) REFERENCES operario(id),
    CONSTRAINT FK_orden_turno    FOREIGN KEY (turno_id)    REFERENCES turno(id)
);

/* ========================= DETALLES DE LA ORDEN =========================
   Todos tienen orden_id con ON DELETE CASCADE: al borrar una orden se
   borran automáticamente sus detalles.
   ======================================================================= */

CREATE TABLE bobina (
    id                  INT IDENTITY(1,1) PRIMARY KEY,
    orden_id            INT NOT NULL,
    codigo              VARCHAR(50)  NULL,
    gramaje             DECIMAL(6,2) NULL,
    ancho_mm            DECIMAL(6,2) NULL,
    diametro_inicial_mm DECIMAL(6,2) NULL,
    diametro_final_mm   DECIMAL(6,2) NULL,
    CONSTRAINT FK_bobina_orden FOREIGN KEY (orden_id)
        REFERENCES orden_produccion(id) ON DELETE CASCADE
);

CREATE TABLE adhesivo (
    id              INT IDENTITY(1,1) PRIMARY KEY,
    orden_id        INT NOT NULL,
    nombre          VARCHAR(50)  NULL,
    tipo            VARCHAR(20)  NULL,
    lote            VARCHAR(50)  NULL,
    fecha_caducidad DATE NULL,
    inicio_kg       DECIMAL(6,2) NULL,
    fin_kg          DECIMAL(6,2) NULL,
    consumo_kg      DECIMAL(6,2) NULL,
    CONSTRAINT FK_adhesivo_orden FOREIGN KEY (orden_id)
        REFERENCES orden_produccion(id) ON DELETE CASCADE
);

CREATE TABLE caja_empaque (
    id                INT IDENTITY(1,1) PRIMARY KEY,
    orden_id          INT NOT NULL,
    nombre            VARCHAR(100) NULL,
    serie             VARCHAR(50)  NULL,
    fecha_fabricacion DATE NULL,
    cantidad          INT NULL,
    CONSTRAINT FK_caja_orden FOREIGN KEY (orden_id)
        REFERENCES orden_produccion(id) ON DELETE CASCADE
);

CREATE TABLE reporte_tiempo (
    id           INT IDENTITY(1,1) PRIMARY KEY,
    orden_id     INT NOT NULL,
    actividad_id INT NULL,               -- referencia a una actividad del catálogo
    hora_inicial TIME NULL,
    hora_final   TIME NULL,
    CONSTRAINT FK_reporte_orden FOREIGN KEY (orden_id)
        REFERENCES orden_produccion(id) ON DELETE CASCADE,
    CONSTRAINT FK_reporte_actividad FOREIGN KEY (actividad_id)
        REFERENCES actividad(id)
);

CREATE TABLE produccion (
    id                       INT IDENTITY(1,1) PRIMARY KEY,
    orden_id                 INT NOT NULL UNIQUE,   -- 1 a 1 con la orden
    bolsas_producidas        INT NULL,
    producto_conforme_kg     DECIMAL(8,2)  NULL,
    desperdicio_papel_kg     DECIMAL(8,2)  NULL,
    desperdicio_refil_kg     DECIMAL(8,2)  NULL,
    metros_lineales          DECIMAL(10,2) NULL,
    cantidad_por_bulto       INT NULL,
    total_cantidad_producida INT NULL,
    observaciones            NVARCHAR(MAX) NULL,
    CONSTRAINT FK_produccion_orden FOREIGN KEY (orden_id)
        REFERENCES orden_produccion(id) ON DELETE CASCADE
);
GO
