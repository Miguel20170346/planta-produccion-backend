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

import java.time.LocalDate;

/**
 * Entidad: representa una fila de la tabla "caja_empaque".
 * Detalle de la orden: MUCHAS cajas -> UNA orden.
 */
@Entity
@Table(name = "caja_empaque")
@Getter
@Setter
public class CajaEmpaque {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "orden_id", nullable = false)
    private OrdenProduccion orden;

    @Column(name = "nombre", length = 100)
    private String nombre;

    @Column(name = "serie", length = 50)
    private String serie;

    @Column(name = "fecha_fabricacion")
    private LocalDate fechaFabricacion;

    @Column(name = "cantidad")
    private Integer cantidad;
}
