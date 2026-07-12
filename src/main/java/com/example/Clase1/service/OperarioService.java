package com.example.Clase1.service;

import com.example.Clase1.dto.OperarioRequest;
import com.example.Clase1.dto.OperarioResponse;
import com.example.Clase1.model.Operario;
import com.example.Clase1.repository.OperarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

/**
 * Logica de negocio de Operario. Mismo patron por capas.
 * Novedad: el numero es opcional, asi que solo validamos duplicado
 * cuando viene informado.
 */
@Service
public class OperarioService {

    private final OperarioRepository operarioRepository;

    public OperarioService(OperarioRepository operarioRepository) {
        this.operarioRepository = operarioRepository;
    }

    @Transactional(readOnly = true)
    public List<OperarioResponse> listar() {
        return operarioRepository.findAll().stream()
                .map(this::aResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public OperarioResponse obtenerPorId(Integer id) {
        return aResponse(buscarOFallar(id));
    }

    @Transactional
    public OperarioResponse crear(OperarioRequest request) {
        validarNumeroUnico(request.numero());
        Operario operario = new Operario();
        operario.setNumero(request.numero());
        operario.setNombre(request.nombre());
        return aResponse(operarioRepository.save(operario));
    }

    @Transactional
    public OperarioResponse actualizar(Integer id, OperarioRequest request) {
        Operario operario = buscarOFallar(id);
        operario.setNumero(request.numero());
        operario.setNombre(request.nombre());
        return aResponse(operarioRepository.save(operario));
    }

    @Transactional
    public void eliminar(Integer id) {
        Operario operario = buscarOFallar(id);
        operarioRepository.delete(operario);
    }

    // ---- Helpers privados ----

    private void validarNumeroUnico(String numero) {
        if (numero != null && !numero.isBlank() && operarioRepository.existsByNumero(numero)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Ya existe un operario con el numero '" + numero + "'");
        }
    }

    private Operario buscarOFallar(Integer id) {
        return operarioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "No existe un operario con id " + id));
    }

    private OperarioResponse aResponse(Operario operario) {
        return new OperarioResponse(operario.getId(), operario.getNumero(), operario.getNombre());
    }
}
