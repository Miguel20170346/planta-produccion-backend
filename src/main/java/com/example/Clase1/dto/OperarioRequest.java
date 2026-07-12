package com.example.Clase1.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO de entrada para crear/actualizar un operario.
 * "numero" es opcional (max 10); "nombre" es obligatorio.
 */
public record OperarioRequest(

        @Size(max = 10, message = "El numero no puede superar los 10 caracteres")
        String numero,

        @NotBlank(message = "El nombre del operario es obligatorio")
        @Size(max = 100, message = "El nombre no puede superar los 100 caracteres")
        String nombre
) {
}
