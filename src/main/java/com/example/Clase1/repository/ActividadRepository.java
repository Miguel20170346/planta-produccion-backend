package com.example.Clase1.repository;

import com.example.Clase1.model.Actividad;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositorio de Actividad.
 */
public interface ActividadRepository extends JpaRepository<Actividad, Integer> {

    boolean existsByCodigo(Integer codigo);
}
