package com.example.Clase1.service;

import com.example.Clase1.dto.ActividadRequest;
import com.example.Clase1.dto.ActividadResponse;
import com.example.Clase1.model.Actividad;
import com.example.Clase1.repository.ActividadRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

/**
 * Logica de negocio de Actividad. Mismo patron por capas.
 * El codigo es opcional: solo validamos duplicado cuando viene.
 */
@Service
public class ActividadService {

    private final ActividadRepository actividadRepository;

    public ActividadService(ActividadRepository actividadRepository) {
        this.actividadRepository = actividadRepository;
    }

    @Transactional(readOnly = true)
    public List<ActividadResponse> listar() {
        return actividadRepository.findAll().stream()
                .map(this::aResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public ActividadResponse obtenerPorId(Integer id) {
        return aResponse(buscarOFallar(id));
    }

    @Transactional
    public ActividadResponse crear(ActividadRequest request) {
        validarCodigoUnico(request.codigo());
        Actividad actividad = new Actividad();
        actividad.setCodigo(request.codigo());
        actividad.setDescripcion(request.descripcion());
        return aResponse(actividadRepository.save(actividad));
    }

    @Transactional
    public ActividadResponse actualizar(Integer id, ActividadRequest request) {
        Actividad actividad = buscarOFallar(id);
        actividad.setCodigo(request.codigo());
        actividad.setDescripcion(request.descripcion());
        return aResponse(actividadRepository.save(actividad));
    }

    @Transactional
    public void eliminar(Integer id) {
        Actividad actividad = buscarOFallar(id);
        actividadRepository.delete(actividad);
    }

    // ---- Helpers privados ----

    private void validarCodigoUnico(Integer codigo) {
        if (codigo != null && actividadRepository.existsByCodigo(codigo)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Ya existe una actividad con el codigo " + codigo);
        }
    }

    private Actividad buscarOFallar(Integer id) {
        return actividadRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "No existe una actividad con id " + id));
    }

    private ActividadResponse aResponse(Actividad actividad) {
        return new ActividadResponse(actividad.getId(), actividad.getCodigo(), actividad.getDescripcion());
    }
}
