package com.example.Clase1.dto;

/**
 * DTO de salida: lo que la API DEVUELVE al cliente.
 * Controlamos exactamente que campos se exponen.
 */
public record TurnoResponse(
        Integer id,
        String nombre
) {
}
