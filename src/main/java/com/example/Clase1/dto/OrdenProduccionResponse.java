package com.example.Clase1.dto;

import com.example.Clase1.model.EstadoOrden;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * DTO de salida para una orden.
 * CLAVE: en la respuesta SI devolvemos objetos anidados (maquina, operario,
 * turno) reutilizando sus DTOs, para que el cliente reciba datos utiles
 * (nombre, etc.) y no solo un id suelto.
 */
public record OrdenProduccionResponse(
        Integer id,
        LocalDate fecha,
        String diseno,
        String opCodigo,
        MaquinaResponse maquina,
        OperarioResponse operario,
        TurnoResponse turno,
        LocalTime horaInicio,
        LocalTime horaFin,
        EstadoOrden estado,
        LocalDateTime creadoEn
) {
}
