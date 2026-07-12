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
 * Entidad: representa una fila de la tabla "operario".
 */
@Entity
@Table(name = "operario")
@Getter
@Setter
public class Operario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "numero", unique = true, length = 10) // opcional, pero unico si viene
    private String numero;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;
}
