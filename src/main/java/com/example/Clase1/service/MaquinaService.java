package com.example.Clase1.service;

import com.example.Clase1.dto.MaquinaRequest;
import com.example.Clase1.dto.MaquinaResponse;
import com.example.Clase1.model.Maquina;
import com.example.Clase1.repository.MaquinaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

/**
 * Logica de negocio de Maquina. Mismo patron que TurnoService.
 */
@Service
public class MaquinaService {

    private final MaquinaRepository maquinaRepository;

    public MaquinaService(MaquinaRepository maquinaRepository) {
        this.maquinaRepository = maquinaRepository;
    }

    @Transactional(readOnly = true)
    public List<MaquinaResponse> listar() {
        return maquinaRepository.findAll().stream()
                .map(this::aResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public MaquinaResponse obtenerPorId(Integer id) {
        return aResponse(buscarOFallar(id));
    }

    @Transactional
    public MaquinaResponse crear(MaquinaRequest request) {
        if (maquinaRepository.existsByNombreIgnoreCase(request.nombre())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Ya existe una maquina con el nombre '" + request.nombre() + "'");
        }
        Maquina maquina = new Maquina();
        maquina.setNombre(request.nombre());
        maquina.setTipo(request.tipo());
        return aResponse(maquinaRepository.save(maquina));
    }

    @Transactional
    public MaquinaResponse actualizar(Integer id, MaquinaRequest request) {
        Maquina maquina = buscarOFallar(id);
        maquina.setNombre(request.nombre());
        maquina.setTipo(request.tipo());
        return aResponse(maquinaRepository.save(maquina));
    }

    @Transactional
    public void eliminar(Integer id) {
        Maquina maquina = buscarOFallar(id);
        maquinaRepository.delete(maquina);
    }

    // ---- Helpers privados ----

    private Maquina buscarOFallar(Integer id) {
        return maquinaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "No existe una maquina con id " + id));
    }

    private MaquinaResponse aResponse(Maquina maquina) {
        return new MaquinaResponse(maquina.getId(), maquina.getNombre(), maquina.getTipo());
    }
}
