package com.example.Clase1.service;

import com.example.Clase1.dto.BobinaRequest;
import com.example.Clase1.dto.BobinaResponse;
import com.example.Clase1.model.Bobina;
import com.example.Clase1.model.OrdenProduccion;
import com.example.Clase1.repository.BobinaRepository;
import com.example.Clase1.repository.OrdenProduccionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

/**
 * Logica de negocio de Bobina. Detalle de la orden.
 */
@Service
public class BobinaService {

    private final BobinaRepository bobinaRepository;
    private final OrdenProduccionRepository ordenRepository;

    public BobinaService(BobinaRepository bobinaRepository,
                         OrdenProduccionRepository ordenRepository) {
        this.bobinaRepository = bobinaRepository;
        this.ordenRepository = ordenRepository;
    }

    /**
     * Lista todas, o solo las de una orden si se pasa ordenId.
     */
    @Transactional(readOnly = true)
    public List<BobinaResponse> listar(Integer ordenId) {
        List<Bobina> bobinas = (ordenId == null)
                ? bobinaRepository.findAll()
                : bobinaRepository.findByOrdenId(ordenId);
        return bobinas.stream().map(this::aResponse).toList();
    }

    @Transactional(readOnly = true)
    public BobinaResponse obtenerPorId(Integer id) {
        return aResponse(buscarOFallar(id));
    }

    @Transactional
    public BobinaResponse crear(BobinaRequest request) {
        Bobina bobina = new Bobina();
        aplicarDatos(bobina, request);
        return aResponse(bobinaRepository.save(bobina));
    }

    @Transactional
    public BobinaResponse actualizar(Integer id, BobinaRequest request) {
        Bobina bobina = buscarOFallar(id);
        aplicarDatos(bobina, request);
        return aResponse(bobinaRepository.save(bobina));
    }

    @Transactional
    public void eliminar(Integer id) {
        Bobina bobina = buscarOFallar(id);
        bobinaRepository.delete(bobina);
    }

    // ---- Helpers privados ----

    private void aplicarDatos(Bobina bobina, BobinaRequest request) {
        validarDiametros(request);
        bobina.setOrden(buscarOrden(request.ordenId()));
        bobina.setCodigo(request.codigo());
        bobina.setGramaje(request.gramaje());
        bobina.setAnchoMm(request.anchoMm());
        bobina.setDiametroInicialMm(request.diametroInicialMm());
        bobina.setDiametroFinalMm(request.diametroFinalMm());
    }

    /**
     * Regla de negocio: el diametro final no puede ser MAYOR que el inicial.
     * A medida que la bobina se consume se desenrolla, asi que el diametro
     * solo puede quedar igual o mas chico.
     */
    private void validarDiametros(BobinaRequest request) {
        if (request.diametroInicialMm() != null && request.diametroFinalMm() != null
                && request.diametroFinalMm().compareTo(request.diametroInicialMm()) > 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "El diametro final no puede ser mayor que el inicial (la bobina se desenrolla).");
        }
    }

    private OrdenProduccion buscarOrden(Integer ordenId) {
        return ordenRepository.findById(ordenId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "No existe una orden con id " + ordenId));
    }

    private Bobina buscarOFallar(Integer id) {
        return bobinaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "No existe una bobina con id " + id));
    }

    private BobinaResponse aResponse(Bobina b) {
        return new BobinaResponse(
                b.getId(),
                b.getOrden().getId(),
                b.getCodigo(),
                b.getGramaje(),
                b.getAnchoMm(),
                b.getDiametroInicialMm(),
                b.getDiametroFinalMm()
        );
    }
}
