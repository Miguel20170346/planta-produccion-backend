package com.example.Clase1.controller;

import com.example.Clase1.dto.ActividadRequest;
import com.example.Clase1.dto.ActividadResponse;
import com.example.Clase1.service.ActividadService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Endpoints REST de Actividad. Base URL: /api/actividades
 */
@RestController
@RequestMapping("/api/actividades")
public class ActividadController {

    private final ActividadService actividadService;

    public ActividadController(ActividadService actividadService) {
        this.actividadService = actividadService;
    }

    @GetMapping
    public List<ActividadResponse> listar() {
        return actividadService.listar();
    }

    @GetMapping("/{id}")
    public ActividadResponse obtener(@PathVariable Integer id) {
        return actividadService.obtenerPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ActividadResponse crear(@Valid @RequestBody ActividadRequest request) {
        return actividadService.crear(request);
    }

    @PutMapping("/{id}")
    public ActividadResponse actualizar(@PathVariable Integer id,
                                        @Valid @RequestBody ActividadRequest request) {
        return actividadService.actualizar(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Integer id) {
        actividadService.eliminar(id);
    }
}
