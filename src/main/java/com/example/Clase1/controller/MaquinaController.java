package com.example.Clase1.controller;

import com.example.Clase1.dto.MaquinaRequest;
import com.example.Clase1.dto.MaquinaResponse;
import com.example.Clase1.service.MaquinaService;
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
 * Endpoints REST de Maquina. Base URL: /api/maquinas
 */
@RestController
@RequestMapping("/api/maquinas")
public class MaquinaController {

    private final MaquinaService maquinaService;

    public MaquinaController(MaquinaService maquinaService) {
        this.maquinaService = maquinaService;
    }

    @GetMapping
    public List<MaquinaResponse> listar() {
        return maquinaService.listar();
    }

    @GetMapping("/{id}")
    public MaquinaResponse obtener(@PathVariable Integer id) {
        return maquinaService.obtenerPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MaquinaResponse crear(@Valid @RequestBody MaquinaRequest request) {
        return maquinaService.crear(request);
    }

    @PutMapping("/{id}")
    public MaquinaResponse actualizar(@PathVariable Integer id,
                                      @Valid @RequestBody MaquinaRequest request) {
        return maquinaService.actualizar(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Integer id) {
        maquinaService.eliminar(id);
    }
}
