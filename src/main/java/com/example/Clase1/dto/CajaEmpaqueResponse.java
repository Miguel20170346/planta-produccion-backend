package com.example.Clase1.dto;

import java.time.LocalDate;

/**
 * DTO de salida para una caja de empaque.
 */
public record CajaEmpaqueResponse(
        Integer id,
        Integer ordenId,
        String nombre,
        String serie,
        LocalDate fechaFabricacion,
        Integer cantidad
) {
}
