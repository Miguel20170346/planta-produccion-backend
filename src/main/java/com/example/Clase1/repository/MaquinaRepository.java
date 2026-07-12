package com.example.Clase1.repository;

import com.example.Clase1.model.Maquina;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositorio de Maquina. CRUD gratis via JpaRepository.
 */
public interface MaquinaRepository extends JpaRepository<Maquina, Integer> {

    boolean existsByNombreIgnoreCase(String nombre);
}
