package com.example.Clase1.dto;

import java.time.LocalTime;

/**
 * DTO de salida para un reporte de tiempo.
 * La actividad se devuelve anidada (reutilizando su DTO) porque su
 * descripcion es util para el cliente; la orden solo como id.
 */
public record ReporteTiempoResponse(
        Integer id,
        Integer ordenId,
        ActividadResponse actividad,
        LocalTime horaInicial,
        LocalTime horaFinal
) {
}
