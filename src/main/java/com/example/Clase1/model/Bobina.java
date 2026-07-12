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
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Entidad: representa una fila de la tabla "bobina".
 * Es un detalle de la orden: MUCHAS bobinas -> UNA orden.
 */
@Entity
@Table(name = "bobina")
@Getter
@Setter
public class Bobina {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "orden_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE) // borrar la orden borra sus bobinas
    private OrdenProduccion orden;

    @Column(name = "codigo", length = 50)
    private String codigo;

    // DECIMAL(6,2) en SQL Server -> BigDecimal en Java (precision exacta)
    @Column(name = "gramaje")
    private BigDecimal gramaje;

    @Column(name = "ancho_mm")
    private BigDecimal anchoMm;

    @Column(name = "diametro_inicial_mm")
    private BigDecimal diametroInicialMm;

    @Column(name = "diametro_final_mm")
    private BigDecimal diametroFinalMm;
}
