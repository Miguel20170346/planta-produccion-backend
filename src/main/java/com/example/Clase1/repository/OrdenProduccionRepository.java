package com.example.Clase1.repository;

import com.example.Clase1.model.OrdenProduccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Repositorio de OrdenProduccion.
 *
 * Extiende JpaSpecificationExecutor para armar la busqueda con filtros
 * OPCIONALES usando la Criteria API (ver OrdenProduccionService). Asi la
 * consulta se construye dinamicamente, solo con los filtros que llegan,
 * y funciona igual en SQL Server (local) y PostgreSQL (produccion).
 */
public interface OrdenProduccionRepository extends JpaRepository<OrdenProduccion, Integer>,
        JpaSpecificationExecutor<OrdenProduccion> {

    boolean existsByOpCodigoIgnoreCase(String opCodigo);
}
