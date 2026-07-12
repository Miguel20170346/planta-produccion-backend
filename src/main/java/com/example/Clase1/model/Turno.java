package com.example.Clase1.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 * Entidad: representa una fila de la tabla "turno" en la base de datos.
 * Cada atributo se mapea a una columna.
 */
@Entity
@Table(name = "turno")
@Getter
@Setter
public class Turno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // coincide con IDENTITY(1,1) de SQL Server
    private Integer id;

    @Column(name = "nombre", nullable = false, unique = true, length = 20)
    private String nombre;
}
