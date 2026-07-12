package com.example.Clase1.controller;

import com.example.Clase1.dto.AdhesivoRequest;
import com.example.Clase1.dto.AdhesivoResponse;
import com.example.Clase1.service.AdhesivoService;
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
 * Endpoints REST de Adhesivo. Base URL: /api/adhesivos
 */
@RestController
@RequestMapping("/api/adhesivos")
public class AdhesivoController {

    private final AdhesivoService adhesivoService;

    public AdhesivoController(AdhesivoService adhesivoService) {
        this.adhesivoService = adhesivoService;
    }

    // GET /api/adhesivos             -> todos
    // GET /api/adhesivos?ordenId=1   -> solo los de esa orden
    @GetMapping
    public List<AdhesivoResponse> listar(@RequestParam(required = false) Integer ordenId) {
        return adhesivoService.listar(ordenId);
    }

    @GetMapping("/{id}")
    public AdhesivoResponse obtener(@PathVariable Integer id) {
        return adhesivoService.obtenerPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AdhesivoResponse crear(@Valid @RequestBody AdhesivoRequest request) {
        return adhesivoService.crear(request);
    }

    @PutMapping("/{id}")
    public AdhesivoResponse actualizar(@PathVariable Integer id,
                                       @Valid @RequestBody AdhesivoRequest request) {
        return adhesivoService.actualizar(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Integer id) {
        adhesivoService.eliminar(id);
    }
}
