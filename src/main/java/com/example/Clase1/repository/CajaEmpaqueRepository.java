package com.example.Clase1.repository;

import com.example.Clase1.model.CajaEmpaque;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repositorio de CajaEmpaque.
 */
public interface CajaEmpaqueRepository extends JpaRepository<CajaEmpaque, Integer> {

    List<CajaEmpaque> findByOrdenId(Integer ordenId);
}
