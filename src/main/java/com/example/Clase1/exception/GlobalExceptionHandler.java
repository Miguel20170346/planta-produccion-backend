package com.example.Clase1.exception;

import com.example.Clase1.dto.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Manejador GLOBAL de errores. Con @RestControllerAdvice, cualquier
 * excepcion lanzada por CUALQUIER controlador pasa por aqui y se
 * convierte en una respuesta JSON limpia y uniforme (sin stack trace).
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Errores que lanzamos a proposito en los servicios:
     * 404 (no encontrado), 409 (conflicto), etc.
     */
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErrorResponse> manejarResponseStatus(ResponseStatusException ex,
                                                               HttpServletRequest request) {
        HttpStatus status = HttpStatus.valueOf(ex.getStatusCode().value());
        ErrorResponse cuerpo = new ErrorResponse(
                LocalDateTime.now(),
                status.value(),
                status.getReasonPhrase(),
                ex.getReason(),
                request.getRequestURI(),
                null
        );
        return ResponseEntity.status(status).body(cuerpo);
    }

    /**
     * Errores de validacion de @Valid en los DTO (400).
     * Junta cada campo invalido con su mensaje.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> manejarValidacion(MethodArgumentNotValidException ex,
                                                          HttpServletRequest request) {
        Map<String, String> errores = new HashMap<>();
        for (FieldError campo : ex.getBindingResult().getFieldErrors()) {
            errores.put(campo.getField(), campo.getDefaultMessage());
        }
        ErrorResponse cuerpo = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                "Error de validacion en los datos enviados",
                request.getRequestURI(),
                errores
        );
        return ResponseEntity.badRequest().body(cuerpo);
    }

    /**
     * Violacion de una restriccion de la BASE DE DATOS (409 Conflicto).
     * Caso tipico: un valor DUPLICADO en una columna unica (ej. un segundo
     * operario sin numero, porque SQL Server permite un solo NULL en columnas
     * UNIQUE). Tambien cubre borrar un registro del que dependen otros (FK).
     * Antes esto caia como 500; ahora devolvemos un 409 claro.
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> manejarIntegridad(DataIntegrityViolationException ex,
                                                           HttpServletRequest request) {
        logger.warn("Violacion de integridad en {}: {}", request.getRequestURI(), ex.getMostSpecificCause().getMessage());
        ErrorResponse cuerpo = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.CONFLICT.value(),
                HttpStatus.CONFLICT.getReasonPhrase(),
                "No se pudo completar la operacion por un conflicto con los datos existentes "
                        + "(posible valor duplicado en un campo unico, o un registro con dependencias).",
                request.getRequestURI(),
                null
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(cuerpo);
    }

    /**
     * El cuerpo JSON no se pudo leer (400). Caso tipico: el JSON esta mal
     * formado, o un campo trae un TIPO equivocado (ej. un texto o un arreglo
     * donde se espera un numero). Antes caia como 500; ahora es un 400 claro.
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> manejarJsonIlegible(HttpMessageNotReadableException ex,
                                                            HttpServletRequest request) {
        logger.warn("JSON ilegible en {}: {}", request.getRequestURI(), ex.getMostSpecificCause().getMessage());
        ErrorResponse cuerpo = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                "El cuerpo de la peticion no se pudo leer: revisa que el JSON este bien formado "
                        + "y que cada campo tenga el tipo de dato correcto.",
                request.getRequestURI(),
                null
        );
        return ResponseEntity.badRequest().body(cuerpo);
    }

    /**
     * Red de seguridad: cualquier error NO previsto (500).
     * Aqui SI registramos el detalle en el log (para diagnosticar),
     * pero al cliente solo le damos un mensaje generico.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> manejarGeneral(Exception ex,
                                                       HttpServletRequest request) {
        logger.error("Error inesperado en {}", request.getRequestURI(), ex);
        ErrorResponse cuerpo = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                "Ocurrio un error inesperado",
                request.getRequestURI(),
                null
        );
        return ResponseEntity.internalServerError().body(cuerpo);
    }
}
