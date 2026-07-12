package com.example.Clase1.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Entidad: representa una fila de la tabla "produccion".
 * NOVEDAD: relacion @OneToOne -> UNA orden tiene UN unico bloque de
 * resultados de produccion (columna orden_id es UNIQUE).
 */
@Entity
@Table(name = "produccion")
@Getter
@Setter
public class Produccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "orden_id", nullable = false, unique = true)
    private OrdenProduccion orden;

    @Column(name = "bolsas_producidas")
    private Integer bolsasProducidas;

    @Column(name = "producto_conforme_kg")
    private BigDecimal productoConformeKg;

    @Column(name = "desperdicio_papel_kg")
    private BigDecimal desperdicioPapelKg;

    @Column(name = "desperdicio_refil_kg")
    private BigDecimal desperdicioRefilKg;

    @Column(name = "metros_lineales")
    private BigDecimal metrosLineales;

    @Column(name = "cantidad_por_bulto")
    private Integer cantidadPorBulto;

    @Column(name = "total_cantidad_producida")
    private Integer totalCantidadProducida;

    @Column(name = "observaciones")
    private String observaciones;
}
