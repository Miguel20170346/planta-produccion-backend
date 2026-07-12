package com.example.Clase1.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalTime;

/**
 * DTO de entrada para crear/actualizar un reporte de tiempo.
 * ordenId es obligatorio; actividadId es opcional.
 */
public record ReporteTiempoRequest(

        @NotNull(message = "La orden es obligatoria")
        Integer ordenId,

        Integer actividadId,

        LocalTime horaInicial,

        LocalTime horaFinal
) {
}
