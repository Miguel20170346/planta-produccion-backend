package com.example.Clase1.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

/**
 * DTO de entrada para crear/actualizar una caja de empaque.
 */
public record CajaEmpaqueRequest(

        @NotNull(message = "La orden es obligatoria")
        Integer ordenId,

        @Size(max = 100, message = "El nombre no puede superar los 100 caracteres")
        String nombre,

        @Size(max = 50, message = "La serie no puede superar los 50 caracteres")
        String serie,

        LocalDate fechaFabricacion,

        @PositiveOrZero(message = "La cantidad no puede ser negativa")
        Integer cantidad
) {
}
