package com.example.Clase1.dto;

/**
 * DTO de salida para una actividad.
 */
public record ActividadResponse(
        Integer id,
        Integer codigo,
        String descripcion
) {
}
