package com.example.Clase1.controller;

import com.example.Clase1.dto.ProduccionRequest;
import com.example.Clase1.dto.ProduccionResponse;
import com.example.Clase1.service.ProduccionService;
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
 * Endpoints REST de Produccion. Base URL: /api/producciones
 */
@RestController
@RequestMapping("/api/producciones")
public class ProduccionController {

    private final ProduccionService produccionService;

    public ProduccionController(ProduccionService produccionService) {
        this.produccionService = produccionService;
    }

    @GetMapping
    public List<ProduccionResponse> listar(@RequestParam(required = false) Integer ordenId) {
        return produccionService.listar(ordenId);
    }

    @GetMapping("/{id}")
    public ProduccionResponse obtener(@PathVariable Integer id) {
        return produccionService.obtenerPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProduccionResponse crear(@Valid @RequestBody ProduccionRequest request) {
        return produccionService.crear(request);
    }

    @PutMapping("/{id}")
    public ProduccionResponse actualizar(@PathVariable Integer id,
                                         @Valid @RequestBody ProduccionRequest request) {
        return produccionService.actualizar(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Integer id) {
        produccionService.eliminar(id);
    }
}
