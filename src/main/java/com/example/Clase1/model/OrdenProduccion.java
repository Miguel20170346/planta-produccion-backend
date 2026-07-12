package com.example.Clase1.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Entidad: la cabecera de la hoja de produccion (tabla "orden_produccion").
 * NOVEDAD: se relaciona con otras entidades via @ManyToOne.
 */
@Entity
@Table(name = "orden_produccion")
@Getter
@Setter
public class OrdenProduccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    @Column(name = "diseno", length = 100)
    private String diseno;

    @Column(name = "op_codigo", nullable = false, unique = true, length = 30)
    private String opCodigo;

    // MUCHAS ordenes -> UNA maquina. Obligatoria (optional = false).
    // fetch LAZY = la maquina no se carga hasta que la pidamos.
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "maquina_id", nullable = false) // columna FK en la tabla
    private Maquina maquina;

    // MUCHAS ordenes -> UN operario. Opcional (puede ser null).
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "operario_id")
    private Operario operario;

    // MUCHAS ordenes -> UN turno. Opcional.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "turno_id")
    private Turno turno;

    @Column(name = "hora_inicio")
    private LocalTime horaInicio;

    @Column(name = "hora_fin")
    private LocalTime horaFin;

    // Estado del ciclo de vida. Se guarda el NOMBRE del enum como texto.
    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false, length = 20)
    private EstadoOrden estado;

    // Lo llena Hibernate al crear el registro; nunca se actualiza.
    @CreationTimestamp
    @Column(name = "creado_en", updatable = false)
    private LocalDateTime creadoEn;
}
