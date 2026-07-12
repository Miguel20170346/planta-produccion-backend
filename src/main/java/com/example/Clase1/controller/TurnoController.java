package com.example.Clase1.controller;

import com.example.Clase1.dto.TurnoRequest;
import com.example.Clase1.dto.TurnoResponse;
import com.example.Clase1.service.TurnoService;
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
 * Controlador: capa web. Expone los endpoints REST.
 * Solo recibe/responde; delega toda la logica en el servicio.
 * Base URL de este recurso: /api/turnos
 */
@RestController
@RequestMapping("/api/turnos")
public class TurnoController {

    private final TurnoService turnoService;

    public TurnoController(TurnoService turnoService) {
        this.turnoService = turnoService;
    }

    // GET /api/turnos  -> lista todos
    @GetMapping
    public List<TurnoResponse> listar() {
        return turnoService.listar();
    }

    // GET /api/turnos/{id}  -> uno por id
    @GetMapping("/{id}")
    public TurnoResponse obtener(@PathVariable Integer id) {
        return turnoService.obtenerPorId(id);
    }

    // POST /api/turnos  -> crea (devuelve 201 Created)
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TurnoResponse crear(@Valid @RequestBody TurnoRequest request) {
        return turnoService.crear(request);
    }

    // PUT /api/turnos/{id}  -> actualiza
    @PutMapping("/{id}")
    public TurnoResponse actualizar(@PathVariable Integer id,
                                    @Valid @RequestBody TurnoRequest request) {
        return turnoService.actualizar(id, request);
    }

    // DELETE /api/turnos/{id}  -> elimina (devuelve 204 No Content)
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Integer id) {
        turnoService.eliminar(id);
    }
}
