package com.example.Clase1.repository;

import com.example.Clase1.model.Adhesivo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repositorio de Adhesivo.
 */
public interface AdhesivoRepository extends JpaRepository<Adhesivo, Integer> {

    List<Adhesivo> findByOrdenId(Integer ordenId);
}
