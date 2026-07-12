package com.example.Clase1.repository;

import com.example.Clase1.model.Produccion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repositorio de Produccion.
 * Como es 1-a-1 con la orden, findByOrdenId devuelve como mucho UNO
 * (por eso Optional, no List).
 */
public interface ProduccionRepository extends JpaRepository<Produccion, Integer> {

    Optional<Produccion> findByOrdenId(Integer ordenId);

    boolean existsByOrdenId(Integer ordenId);
}
