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

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Entidad: representa una fila de la tabla "adhesivo".
 * Detalle de la orden: MUCHOS adhesivos -> UNA orden.
 */
@Entity
@Table(name = "adhesivo")
@Getter
@Setter
public class Adhesivo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "orden_id", nullable = false)
    private OrdenProduccion orden;

    @Column(name = "nombre", length = 50)
    private String nombre;

    @Column(name = "tipo", length = 20) // 'FONDO' / 'LATERAL'
    private String tipo;

    @Column(name = "lote", length = 50)
    private String lote;

    @Column(name = "fecha_caducidad")
    private LocalDate fechaCaducidad;

    @Column(name = "inicio_kg")
    private BigDecimal inicioKg;

    @Column(name = "fin_kg")
    private BigDecimal finKg;

    @Column(name = "consumo_kg")
    private BigDecimal consumoKg;
}
