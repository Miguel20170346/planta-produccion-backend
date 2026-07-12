package com.example.Clase1.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * DTO de entrada para crear/actualizar un adhesivo.
 * consumoKg es opcional: si no viene, el service lo calcula (inicio - fin).
 */
public record AdhesivoRequest(

        @NotNull(message = "La orden es obligatoria")
        Integer ordenId,

        @Size(max = 50, message = "El nombre no puede superar los 50 caracteres")
        String nombre,

        @Size(max = 20, message = "El tipo no puede superar los 20 caracteres")
        String tipo,

        @Size(max = 50, message = "El lote no puede superar los 50 caracteres")
        String lote,

        LocalDate fechaCaducidad,

        @PositiveOrZero(message = "El inicio en kg no puede ser negativo")
        BigDecimal inicioKg,

        @PositiveOrZero(message = "El fin en kg no puede ser negativo")
        BigDecimal finKg,

        @PositiveOrZero(message = "El consumo en kg no puede ser negativo")
        BigDecimal consumoKg
) {
}
