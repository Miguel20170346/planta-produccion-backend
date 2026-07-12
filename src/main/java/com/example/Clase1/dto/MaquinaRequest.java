package com.example.Clase1.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO de entrada para crear/actualizar una maquina.
 * "tipo" es opcional, pero si viene no debe pasar de 50 caracteres.
 */
public record MaquinaRequest(

        @NotBlank(message = "El nombre de la maquina es obligatorio")
        @Size(max = 50, message = "El nombre no puede superar los 50 caracteres")
        String nombre,

        @Size(max = 50, message = "El tipo no puede superar los 50 caracteres")
        String tipo
) {
}
