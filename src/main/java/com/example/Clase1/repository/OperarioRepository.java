package com.example.Clase1.repository;

import com.example.Clase1.model.Operario;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositorio de Operario.
 */
public interface OperarioRepository extends JpaRepository<Operario, Integer> {

    boolean existsByNumero(String numero);
}
