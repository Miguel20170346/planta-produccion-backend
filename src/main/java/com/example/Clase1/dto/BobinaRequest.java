package com.example.Clase1.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

/**
 * DTO de entrada para crear/actualizar una bobina.
 * Se relaciona con la orden por su id.
 */
public record BobinaRequest(

        @NotNull(message = "La orden es obligatoria")
        Integer ordenId,

        @Size(max = 50, message = "El codigo no puede superar los 50 caracteres")
        String codigo,

        @PositiveOrZero(message = "El gramaje no puede ser negativo")
        BigDecimal gramaje,

        @PositiveOrZero(message = "El ancho no puede ser negativo")
        BigDecimal anchoMm,

        @PositiveOrZero(message = "El diametro inicial no puede ser negativo")
        BigDecimal diametroInicialMm,

        @PositiveOrZero(message = "El diametro final no puede ser negativo")
        BigDecimal diametroFinalMm
) {
}
