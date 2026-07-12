package com.example.Clase1.repository;

import com.example.Clase1.model.EstadoOrden;
import com.example.Clase1.model.OrdenProduccion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

/**
 * Repositorio de OrdenProduccion.
 */
public interface OrdenProduccionRepository extends JpaRepository<OrdenProduccion, Integer> {

    boolean existsByOpCodigoIgnoreCase(String opCodigo);

    /**
     * Busqueda PAGINADA con filtros OPCIONALES.
     * - Si un parametro llega null, esa condicion se ignora (IS NULL en el WHERE).
     * - busqueda: coincide con el codigo O el diseno (sin distinguir mayusculas).
     * - maquinaId: limita a una maquina concreta.
     * - desde / hasta: acotan por rango de fecha (inclusive).
     * El Pageable trae el numero de pagina, el tamano y el orden.
     */
    @Query("SELECT o FROM OrdenProduccion o WHERE "
            + "(:busqueda IS NULL OR LOWER(o.opCodigo) LIKE LOWER(CONCAT('%', :busqueda, '%')) "
            + "  OR LOWER(o.diseno) LIKE LOWER(CONCAT('%', :busqueda, '%'))) "
            + "AND (:maquinaId IS NULL OR o.maquina.id = :maquinaId) "
            + "AND (:estado IS NULL OR o.estado = :estado) "
            + "AND (:desde IS NULL OR o.fecha >= :desde) "
            + "AND (:hasta IS NULL OR o.fecha <= :hasta)")
    Page<OrdenProduccion> buscar(@Param("busqueda") String busqueda,
                                 @Param("maquinaId") Integer maquinaId,
                                 @Param("estado") EstadoOrden estado,
                                 @Param("desde") LocalDate desde,
                                 @Param("hasta") LocalDate hasta,
                                 Pageable pageable);
}
