package com.example.Clase1.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * DTO de salida para un adhesivo.
 */
public record AdhesivoResponse(
        Integer id,
        Integer ordenId,
        String nombre,
        String tipo,
        String lote,
        LocalDate fechaCaducidad,
        BigDecimal inicioKg,
        BigDecimal finKg,
        BigDecimal consumoKg
) {
}
