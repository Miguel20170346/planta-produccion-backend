package com.example.Clase1.model;

/**
 * Estado del ciclo de vida de una orden de produccion.
 * Se guarda como texto en la columna "estado" (@Enumerated(STRING)).
 */
public enum EstadoOrden {
    EN_PROCESO,   // la orden esta abierta, todavia se le cargan datos
    TERMINADA,    // la corrida cerro, los datos estan completos
    CANCELADA     // la orden se anulo antes de terminar
}
