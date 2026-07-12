package com.example.Clase1.repository;

import com.example.Clase1.model.Turno;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositorio: capa de acceso a datos.
 * Al extender JpaRepository, Spring genera SOLO la implementacion con
 * findAll(), findById(), save(), deleteById(), existsById(), etc.
 * <Turno, Integer> = entidad que maneja y tipo de su id (PK).
 */
public interface TurnoRepository extends JpaRepository<Turno, Integer> {

    // Metodo "derivado": Spring crea la consulta a partir del nombre.
    // Se traduce a: SELECT ... WHERE LOWER(nombre) = LOWER(?)
    boolean existsByNombreIgnoreCase(String nombre);
}
