package com.example.Clase1.dto;

import com.example.Clase1.model.EstadoOrden;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * DTO de entrada para crear/actualizar una orden.
 * CLAVE: para las relaciones el cliente NO manda objetos anidados,
 * manda solo los IDs (maquinaId, operarioId, turnoId).
 */
public record OrdenProduccionRequest(

        @NotNull(message = "La fecha es obligatoria")
        LocalDate fecha,

        @Size(max = 100, message = "El diseno no puede superar los 100 caracteres")
        String diseno,

        @NotBlank(message = "El codigo de la orden (op_codigo) es obligatorio")
        @Size(max = 30, message = "El codigo no puede superar los 30 caracteres")
        String opCodigo,

        @NotNull(message = "La maquina es obligatoria")
        Integer maquinaId,

        Integer operarioId,   // opcional

        Integer turnoId,      // opcional

        LocalTime horaInicio,

        LocalTime horaFin,

        // Opcional: si no viene, el servicio la deja EN_PROCESO al crear.
        EstadoOrden estado
) {
}
