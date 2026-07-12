package com.example.Clase1.dto;

import java.math.BigDecimal;

/**
 * DTO de salida para una bobina.
 * Devolvemos solo el ordenId (no la orden completa) para no anidar de mas.
 */
public record BobinaResponse(
        Integer id,
        Integer ordenId,
        String codigo,
        BigDecimal gramaje,
        BigDecimal anchoMm,
        BigDecimal diametroInicialMm,
        BigDecimal diametroFinalMm
) {
}
