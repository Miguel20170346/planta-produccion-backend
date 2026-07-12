package com.example.Clase1.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

/**
 * DTO de entrada para crear/actualizar el bloque de produccion de una orden.
 */
public record ProduccionRequest(

        @NotNull(message = "La orden es obligatoria")
        Integer ordenId,

        @PositiveOrZero(message = "Las bolsas producidas no pueden ser negativas")
        Integer bolsasProducidas,

        @PositiveOrZero(message = "El producto conforme no puede ser negativo")
        BigDecimal productoConformeKg,

        @PositiveOrZero(message = "El desperdicio de papel no puede ser negativo")
        BigDecimal desperdicioPapelKg,

        @PositiveOrZero(message = "El desperdicio de refil no puede ser negativo")
        BigDecimal desperdicioRefilKg,

        @PositiveOrZero(message = "Los metros lineales no pueden ser negativos")
        BigDecimal metrosLineales,

        @PositiveOrZero(message = "La cantidad por bulto no puede ser negativa")
        Integer cantidadPorBulto,

        @PositiveOrZero(message = "El total producido no puede ser negativo")
        Integer totalCantidadProducida,

        String observaciones
) {
}
