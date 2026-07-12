package com.example.Clase1.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

/**
 * Entidad: representa una fila de la tabla "reporte_tiempo".
 * NOVEDAD: tiene DOS relaciones @ManyToOne:
 *  - orden (obligatoria)
 *  - actividad (opcional)
 */
@Entity
@Table(name = "reporte_tiempo")
@Getter
@Setter
public class ReporteTiempo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "orden_id", nullable = false)
    private OrdenProduccion orden;

    @ManyToOne(fetch = FetchType.LAZY) // opcional (puede ser null)
    @JoinColumn(name = "actividad_id")
    private Actividad actividad;

    @Column(name = "hora_inicial")
    private LocalTime horaInicial;

    @Column(name = "hora_final")
    private LocalTime horaFinal;
}
