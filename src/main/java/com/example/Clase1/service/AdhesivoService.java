package com.example.Clase1.service;

import com.example.Clase1.dto.AdhesivoRequest;
import com.example.Clase1.dto.AdhesivoResponse;
import com.example.Clase1.model.Adhesivo;
import com.example.Clase1.model.OrdenProduccion;
import com.example.Clase1.repository.AdhesivoRepository;
import com.example.Clase1.repository.OrdenProduccionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;

/**
 * Logica de negocio de Adhesivo. Detalle de la orden.
 * Regla: si no mandan consumoKg pero si inicio y fin, se calcula solo.
 */
@Service
public class AdhesivoService {

    private final AdhesivoRepository adhesivoRepository;
    private final OrdenProduccionRepository ordenRepository;

    public AdhesivoService(AdhesivoRepository adhesivoRepository,
                           OrdenProduccionRepository ordenRepository) {
        this.adhesivoRepository = adhesivoRepository;
        this.ordenRepository = ordenRepository;
    }

    @Transactional(readOnly = true)
    public List<AdhesivoResponse> listar(Integer ordenId) {
        List<Adhesivo> adhesivos = (ordenId == null)
                ? adhesivoRepository.findAll()
                : adhesivoRepository.findByOrdenId(ordenId);
        return adhesivos.stream().map(this::aResponse).toList();
    }

    @Transactional(readOnly = true)
    public AdhesivoResponse obtenerPorId(Integer id) {
        return aResponse(buscarOFallar(id));
    }

    @Transactional
    public AdhesivoResponse crear(AdhesivoRequest request) {
        Adhesivo adhesivo = new Adhesivo();
        aplicarDatos(adhesivo, request);
        return aResponse(adhesivoRepository.save(adhesivo));
    }

    @Transactional
    public AdhesivoResponse actualizar(Integer id, AdhesivoRequest request) {
        Adhesivo adhesivo = buscarOFallar(id);
        aplicarDatos(adhesivo, request);
        return aResponse(adhesivoRepository.save(adhesivo));
    }

    @Transactional
    public void eliminar(Integer id) {
        Adhesivo adhesivo = buscarOFallar(id);
        adhesivoRepository.delete(adhesivo);
    }

    // ---- Helpers privados ----

    private void aplicarDatos(Adhesivo adhesivo, AdhesivoRequest request) {
        validarConsumo(request);
        adhesivo.setOrden(buscarOrden(request.ordenId()));
        adhesivo.setNombre(request.nombre());
        adhesivo.setTipo(request.tipo());
        adhesivo.setLote(request.lote());
        adhesivo.setFechaCaducidad(request.fechaCaducidad());
        adhesivo.setInicioKg(request.inicioKg());
        adhesivo.setFinKg(request.finKg());
        adhesivo.setConsumoKg(calcularConsumo(request));
    }

    /**
     * Regla de negocio: el kg final no puede ser MAYOR que el inicial,
     * porque el consumo (inicio - fin) quedaria negativo. (Se gasta adhesivo,
     * asi que al final siempre hay igual o menos que al principio.)
     */
    private void validarConsumo(AdhesivoRequest request) {
        if (request.inicioKg() != null && request.finKg() != null
                && request.finKg().compareTo(request.inicioKg()) > 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "El kg final (" + request.finKg() + ") no puede ser mayor que el inicial ("
                            + request.inicioKg() + "): el consumo seria negativo.");
        }
    }

    /**
     * Si el consumo viene informado, se respeta.
     * Si no, pero hay inicio y fin, se calcula como inicio - fin.
     */
    private BigDecimal calcularConsumo(AdhesivoRequest request) {
        if (request.consumoKg() != null) {
            return request.consumoKg();
        }
        if (request.inicioKg() != null && request.finKg() != null) {
            return request.inicioKg().subtract(request.finKg());
        }
        return null;
    }

    private OrdenProduccion buscarOrden(Integer ordenId) {
        return ordenRepository.findById(ordenId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "No existe una orden con id " + ordenId));
    }

    private Adhesivo buscarOFallar(Integer id) {
        return adhesivoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "No existe un adhesivo con id " + id));
    }

    private AdhesivoResponse aResponse(Adhesivo a) {
        return new AdhesivoResponse(
                a.getId(),
                a.getOrden().getId(),
                a.getNombre(),
                a.getTipo(),
                a.getLote(),
                a.getFechaCaducidad(),
                a.getInicioKg(),
                a.getFinKg(),
                a.getConsumoKg()
        );
    }
}
