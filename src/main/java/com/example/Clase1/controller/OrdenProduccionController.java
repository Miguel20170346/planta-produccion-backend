package com.example.Clase1.controller;

import com.example.Clase1.dto.OrdenProduccionRequest;
import com.example.Clase1.dto.OrdenProduccionResponse;
import com.example.Clase1.dto.PaginaOrdenesResponse;
import com.example.Clase1.model.EstadoOrden;
import com.example.Clase1.service.OrdenProduccionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

/**
 * Endpoints REST de OrdenProduccion. Base URL: /api/ordenes
 */
@RestController
@RequestMapping("/api/ordenes")
public class OrdenProduccionController {

    private final OrdenProduccionService ordenService;

    public OrdenProduccionController(OrdenProduccionService ordenService) {
        this.ordenService = ordenService;
    }

    @GetMapping
    public List<OrdenProduccionResponse> listar() {
        return ordenService.listar();
    }

    /**
     * Lista PAGINADA con filtros opcionales. Ej:
     *   /api/ordenes/pagina?pagina=0&tamano=20&busqueda=tazo&maquinaId=3
     *   &desde=2026-05-01&hasta=2026-05-31
     * Las fechas van en formato ISO (yyyy-MM-dd).
     */
    @GetMapping("/pagina")
    public PaginaOrdenesResponse listarPagina(
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue = "20") int tamano,
            @RequestParam(required = false) String busqueda,
            @RequestParam(required = false) Integer maquinaId,
            @RequestParam(required = false) EstadoOrden estado,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate desde,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate hasta) {
        return ordenService.listarPagina(pagina, tamano, busqueda, maquinaId, estado, desde, hasta);
    }

    @GetMapping("/{id}")
    public OrdenProduccionResponse obtener(@PathVariable Integer id) {
        return ordenService.obtenerPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrdenProduccionResponse crear(@Valid @RequestBody OrdenProduccionRequest request) {
        return ordenService.crear(request);
    }

    @PutMapping("/{id}")
    public OrdenProduccionResponse actualizar(@PathVariable Integer id,
                                              @Valid @RequestBody OrdenProduccionRequest request) {
        return ordenService.actualizar(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Integer id) {
        ordenService.eliminar(id);
    }
}
