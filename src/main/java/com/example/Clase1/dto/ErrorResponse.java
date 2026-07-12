package com.example.Clase1.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * DTO de salida para los errores. Formato UNICO para toda la API.
 * @JsonInclude(NON_NULL) = los campos en null no aparecen en el JSON
 * (asi "errores" solo se muestra cuando hay validaciones de campos).
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErrorResponse(
        LocalDateTime timestamp,
        int status,
        String error,
        String mensaje,
        String path,
        Map<String, String> errores
) {
}
