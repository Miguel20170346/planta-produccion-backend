package com.example.Clase1.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO de entrada: lo que el cliente ENVIA al crear/actualizar un turno.
 * No exponemos la entidad directamente. Aqui van las validaciones.
 */
public record TurnoRequest(

        @NotBlank(message = "El nombre del turno es obligatorio")
        @Size(max = 20, message = "El nombre no puede superar los 20 caracteres")
        String nombre
) {
}
