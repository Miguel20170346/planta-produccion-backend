package com.example.Clase1.dto;

/**
 * DTO de salida para un operario.
 */
public record OperarioResponse(
        Integer id,
        String numero,
        String nombre
) {
}
