package com.example.Clase1.service;

import com.example.Clase1.dto.TurnoRequest;
import com.example.Clase1.dto.TurnoResponse;
import com.example.Clase1.model.Turno;
import com.example.Clase1.repository.TurnoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

/**
 * Servicio: capa de logica de negocio.
 * Aqui van las reglas (ej: no permitir turnos duplicados) y la
 * conversion entre Entidad <-> DTO. El controlador nunca habla
 * directo con el repositorio.
 */
@Service
public class TurnoService {

    private final TurnoRepository turnoRepository;

    // Inyeccion por constructor (recomendado): dependencias explicitas e inmutables.
    public TurnoService(TurnoRepository turnoRepository) {
        this.turnoRepository = turnoRepository;
    }

    @Transactional(readOnly = true)
    public List<TurnoResponse> listar() {
        return turnoRepository.findAll().stream()
                .map(this::aResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public TurnoResponse obtenerPorId(Integer id) {
        Turno turno = buscarOFallar(id);
        return aResponse(turno);
    }

    @Transactional
    public TurnoResponse crear(TurnoRequest request) {
        if (turnoRepository.existsByNombreIgnoreCase(request.nombre())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Ya existe un turno con el nombre '" + request.nombre() + "'");
        }
        Turno turno = new Turno();
        turno.setNombre(request.nombre());
        return aResponse(turnoRepository.save(turno));
    }

    @Transactional
    public TurnoResponse actualizar(Integer id, TurnoRequest request) {
        Turno turno = buscarOFallar(id);
        turno.setNombre(request.nombre());
        return aResponse(turnoRepository.save(turno));
    }

    @Transactional
    public void eliminar(Integer id) {
        Turno turno = buscarOFallar(id);
        turnoRepository.delete(turno);
    }

    // ---- Helpers privados ----

    private Turno buscarOFallar(Integer id) {
        return turnoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "No existe un turno con id " + id));
    }

    private TurnoResponse aResponse(Turno turno) {
        return new TurnoResponse(turno.getId(), turno.getNombre());
    }
}
