package com.example.Clase1.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

/**
 * DTO de entrada para crear/actualizar una actividad.
 * "codigo" es opcional; si viene, debe ser un numero positivo.
 * "descripcion" es obligatoria.
 */
public record ActividadRequest(

        @Positive(message = "El codigo debe ser un numero positivo")
        Integer codigo,

        @NotBlank(message = "La descripcion es obligatoria")
        @Size(max = 100, message = "La descripcion no puede superar los 100 caracteres")
        String descripcion
) {
}
