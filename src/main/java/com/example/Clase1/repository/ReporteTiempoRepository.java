package com.example.Clase1.repository;

import com.example.Clase1.model.ReporteTiempo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repositorio de ReporteTiempo.
 */
public interface ReporteTiempoRepository extends JpaRepository<ReporteTiempo, Integer> {

    List<ReporteTiempo> findByOrdenId(Integer ordenId);
}
