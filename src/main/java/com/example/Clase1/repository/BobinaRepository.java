package com.example.Clase1.repository;

import com.example.Clase1.model.Bobina;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repositorio de Bobina.
 */
public interface BobinaRepository extends JpaRepository<Bobina, Integer> {

    // Metodo derivado: busca todas las bobinas de una orden.
    // Spring lo traduce a: SELECT ... WHERE orden_id = ?
    List<Bobina> findByOrdenId(Integer ordenId);
}
