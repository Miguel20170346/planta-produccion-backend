package com.example.Clase1.controller;

import com.example.Clase1.dto.CajaEmpaqueRequest;
import com.example.Clase1.dto.CajaEmpaqueResponse;
import com.example.Clase1.service.CajaEmpaqueService;
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
 * Endpoints REST de CajaEmpaque. Base URL: /api/cajas
 */
@RestController
@RequestMapping("/api/cajas")
public class CajaEmpaqueController {

    private final CajaEmpaqueService cajaService;

    public CajaEmpaqueController(CajaEmpaqueService cajaService) {
        this.cajaService = cajaService;
    }

    @GetMapping
    public List<CajaEmpaqueResponse> listar(@RequestParam(required = false) Integer ordenId) {
        return cajaService.listar(ordenId);
    }

    @GetMapping("/{id}")
    public CajaEmpaqueResponse obtener(@PathVariable Integer id) {
        return cajaService.obtenerPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CajaEmpaqueResponse crear(@Valid @RequestBody CajaEmpaqueRequest request) {
        return cajaService.crear(request);
    }

    @PutMapping("/{id}")
    public CajaEmpaqueResponse actualizar(@PathVariable Integer id,
                                          @Valid @RequestBody CajaEmpaqueRequest request) {
        return cajaService.actualizar(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Integer id) {
        cajaService.eliminar(id);
    }
}
