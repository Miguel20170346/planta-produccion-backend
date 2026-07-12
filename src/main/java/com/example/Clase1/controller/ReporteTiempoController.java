package com.example.Clase1.controller;

import com.example.Clase1.dto.ReporteTiempoRequest;
import com.example.Clase1.dto.ReporteTiempoResponse;
import com.example.Clase1.service.ReporteTiempoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Endpoints REST de ReporteTiempo. Base URL: /api/reportes-tiempo
 */
@RestController
@RequestMapping("/api/reportes-tiempo")
public class ReporteTiempoController {

    private final ReporteTiempoService reporteService;

    public ReporteTiempoController(ReporteTiempoService reporteService) {
        this.reporteService = reporteService;
    }

    @GetMapping
    public List<ReporteTiempoResponse> listar(@RequestParam(required = false) Integer ordenId) {
        return reporteService.listar(ordenId);
    }

    @GetMapping("/{id}")
    public ReporteTiempoResponse obtener(@PathVariable Integer id) {
        return reporteService.obtenerPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReporteTiempoResponse crear(@Valid @RequestBody ReporteTiempoRequest request) {
        return reporteService.crear(request);
    }

    @PutMapping("/{id}")
    public ReporteTiempoResponse actualizar(@PathVariable Integer id,
                                            @Valid @RequestBody ReporteTiempoRequest request) {
        return reporteService.actualizar(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Integer id) {
        reporteService.eliminar(id);
    }
}
