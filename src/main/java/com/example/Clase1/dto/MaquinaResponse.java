package com.example.Clase1.dto;

/**
 * DTO de salida para una maquina.
 */
public record MaquinaResponse(
        Integer id,
        String nombre,
        String tipo
) {
}
