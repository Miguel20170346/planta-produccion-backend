# Sistema de Producción — Backend (API REST)

API REST para **digitalizar la hoja "Control diario de producción"** de una planta
manufacturera de empaques y embalajes de cartón. Reemplaza el formulario en papel que
se llena en cada turno: registra las órdenes de producción con todo su detalle (materia
prima, tiempos, resultado) y expone los datos para consultarlos y reportarlos.

Este repositorio es el **backend**. El frontend (React) vive en un repositorio aparte:
👉 [planta-frontend](../planta-frontend).

---

## 🧰 Tecnologías

- **Java 17** · **Spring Boot 4.1**
- **Spring Web (MVC)** — API REST
- **Spring Data JPA + Hibernate** — acceso a datos
- **Bean Validation** — validación de datos de entrada
- **SQL Server** — base de datos relacional
- **Lombok** — menos código repetitivo
- **Maven** — build y dependencias

---

## ✨ Características

- **CRUD completo** de toda la hoja de producción (10 entidades relacionadas).
- **Órdenes de producción** con su detalle: bobinas, adhesivos, cajas de empaque,
  reportes de tiempo y bloque de producción (relación 1‑a‑1).
- **Catálogos** reutilizables: turnos, máquinas, operarios y actividades.
- **Listado paginado** de órdenes con filtros combinables: texto (código/diseño),
  máquina, estado y **rango de fechas**.
- **Estados de la orden**: `EN_PROCESO`, `TERMINADA`, `CANCELADA`.
- **Reglas de negocio** validadas en el servicio (p. ej. el consumo de adhesivo no
  puede ser negativo, la hora de fin no puede ser igual a la de inicio).
- **Manejo de errores centralizado**: respuestas JSON uniformes con el código HTTP
  correcto (400 validación, 404 no encontrado, 409 conflicto, etc.).
- **Borrado en cascada**: al eliminar una orden se eliminan todos sus detalles.
- **CORS** habilitado para el frontend.

---

## 🏗️ Arquitectura

Arquitectura **por capas**, con los paquetes organizados por responsabilidad:

```
com.example.Clase1
├── model        → entidades JPA (mapean las tablas)
├── repository   → interfaces JpaRepository (acceso a datos)
├── service      → lógica de negocio y reglas de validación
├── controller   → endpoints REST
├── dto          → objetos de entrada (Request) y salida (Response)
├── exception    → manejador global de errores
└── config       → configuración (CORS)
```

El flujo de una petición es: **Controller → Service → Repository → Base de datos**, y
la respuesta vuelve convertida a DTOs (nunca se exponen las entidades directamente).

### Modelo de datos

10 tablas: 4 catálogos + la orden + 5 tablas de detalle.

- **Catálogos** (datos compartidos): `turno`, `maquina`, `operario`, `actividad`.
- **`orden_produccion`** — la cabecera; referencia a máquina, operario y turno.
- **Detalles** (pertenecen a una orden, se borran en cascada con ella):
  `bobina`, `adhesivo`, `caja_empaque`, `reporte_tiempo`, `produccion`.

> El esquema lo administra un script SQL (`spring.jpa.hibernate.ddl-auto=none`):
> Hibernate no modifica la estructura, solo la mapea.

---

## 🔌 Endpoints principales

Base URL: `http://localhost:8080/api`

| Recurso            | Endpoints                                                            |
|--------------------|---------------------------------------------------------------------|
| Catálogos          | `/turnos` · `/maquinas` · `/operarios` · `/actividades`             |
| Órdenes            | `/ordenes` — GET, POST, PUT `/{id}`, DELETE `/{id}`                  |
| Órdenes (paginado) | `/ordenes/pagina?pagina&tamano&busqueda&maquinaId&estado&desde&hasta` |
| Detalles de orden  | `/bobinas` · `/adhesivos` · `/cajas` · `/reportes-tiempo` · `/producciones` (filtran por `?ordenId`) |

Todos los recursos soportan las operaciones REST estándar: `GET` (listar), `GET /{id}`
(uno), `POST` (crear), `PUT /{id}` (actualizar) y `DELETE /{id}` (eliminar).

---

## 🚀 Cómo ejecutarlo

### Requisitos

- JDK 17+
- SQL Server con la base de datos `PlantaProduccion` creada (ver el script del esquema).

### Configuración

Editar `src/main/resources/application.properties` con los datos de tu SQL Server:

```properties
spring.datasource.url=jdbc:sqlserver://localhost:1433;databaseName=PlantaProduccion;encrypt=true;trustServerCertificate=true
spring.datasource.username=TU_USUARIO
spring.datasource.password=TU_CONTRASEÑA
```

### Ejecutar

```bash
# En la raíz del proyecto (usa el Maven Wrapper incluido)
./mvnw spring-boot:run
```

La API queda disponible en `http://localhost:8080`.

---

## 📄 Licencia

Proyecto de aprendizaje / portafolio.
