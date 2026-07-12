package com.example.Clase1.controller;

import com.example.Clase1.dto.BobinaRequest;
import com.example.Clase1.dto.BobinaResponse;
import com.example.Clase1.service.BobinaService;
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
 * Endpoints REST de Bobina. Base URL: /api/bobinas
 */
@RestController
@RequestMapping("/api/bobinas")
public class BobinaController {

    private final BobinaService bobinaService;

    public BobinaController(BobinaService bobinaService) {
        this.bobinaService = bobinaService;
    }

    // GET /api/bobinas            -> todas
    // GET /api/bobinas?ordenId=1  -> solo las de esa orden
    @GetMapping
    public List<BobinaResponse> listar(@RequestParam(required = false) Integer ordenId) {
        return bobinaService.listar(ordenId);
    }

    @GetMapping("/{id}")
    public BobinaResponse obtener(@PathVariable Integer id) {
        return bobinaService.obtenerPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BobinaResponse crear(@Valid @RequestBody BobinaRequest request) {
        return bobinaService.crear(request);
    }

    @PutMapping("/{id}")
    public BobinaResponse actualizar(@PathVariable Integer id,
                                     @Valid @RequestBody BobinaRequest request) {
        return bobinaService.actualizar(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Integer id) {
        bobinaService.eliminar(id);
    }
}
