package com.example.Clase1.dto;

import java.math.BigDecimal;

/**
 * DTO de salida para el bloque de produccion.
 */
public record ProduccionResponse(
        Integer id,
        Integer ordenId,
        Integer bolsasProducidas,
        BigDecimal productoConformeKg,
        BigDecimal desperdicioPapelKg,
        BigDecimal desperdicioRefilKg,
        BigDecimal metrosLineales,
        Integer cantidadPorBulto,
        Integer totalCantidadProducida,
        String observaciones
) {
}
