package com.example.Clase1.controller;

import com.example.Clase1.dto.OperarioRequest;
import com.example.Clase1.dto.OperarioResponse;
import com.example.Clase1.service.OperarioService;
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
 * Endpoints REST de Operario. Base URL: /api/operarios
 */
@RestController
@RequestMapping("/api/operarios")
public class OperarioController {

    private final OperarioService operarioService;

    public OperarioController(OperarioService operarioService) {
        this.operarioService = operarioService;
    }

    @GetMapping
    public List<OperarioResponse> listar() {
        return operarioService.listar();
    }

    @GetMapping("/{id}")
    public OperarioResponse obtener(@PathVariable Integer id) {
        return operarioService.obtenerPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OperarioResponse crear(@Valid @RequestBody OperarioRequest request) {
        return operarioService.crear(request);
    }

    @PutMapping("/{id}")
    public OperarioResponse actualizar(@PathVariable Integer id,
                                       @Valid @RequestBody OperarioRequest request) {
        return operarioService.actualizar(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Integer id) {
        operarioService.eliminar(id);
    }
}
