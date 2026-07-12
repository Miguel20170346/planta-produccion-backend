package com.example.Clase1.service;

import com.example.Clase1.dto.CajaEmpaqueRequest;
import com.example.Clase1.dto.CajaEmpaqueResponse;
import com.example.Clase1.model.CajaEmpaque;
import com.example.Clase1.model.OrdenProduccion;
import com.example.Clase1.repository.CajaEmpaqueRepository;
import com.example.Clase1.repository.OrdenProduccionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

/**
 * Logica de negocio de CajaEmpaque. Detalle de la orden.
 */
@Service
public class CajaEmpaqueService {

    private final CajaEmpaqueRepository cajaRepository;
    private final OrdenProduccionRepository ordenRepository;

    public CajaEmpaqueService(CajaEmpaqueRepository cajaRepository,
                              OrdenProduccionRepository ordenRepository) {
        this.cajaRepository = cajaRepository;
        this.ordenRepository = ordenRepository;
    }

    @Transactional(readOnly = true)
    public List<CajaEmpaqueResponse> listar(Integer ordenId) {
        List<CajaEmpaque> cajas = (ordenId == null)
                ? cajaRepository.findAll()
                : cajaRepository.findByOrdenId(ordenId);
        return cajas.stream().map(this::aResponse).toList();
    }

    @Transactional(readOnly = true)
    public CajaEmpaqueResponse obtenerPorId(Integer id) {
        return aResponse(buscarOFallar(id));
    }

    @Transactional
    public CajaEmpaqueResponse crear(CajaEmpaqueRequest request) {
        CajaEmpaque caja = new CajaEmpaque();
        aplicarDatos(caja, request);
        return aResponse(cajaRepository.save(caja));
    }

    @Transactional
    public CajaEmpaqueResponse actualizar(Integer id, CajaEmpaqueRequest request) {
        CajaEmpaque caja = buscarOFallar(id);
        aplicarDatos(caja, request);
        return aResponse(cajaRepository.save(caja));
    }

    @Transactional
    public void eliminar(Integer id) {
        CajaEmpaque caja = buscarOFallar(id);
        cajaRepository.delete(caja);
    }

    // ---- Helpers privados ----

    private void aplicarDatos(CajaEmpaque caja, CajaEmpaqueRequest request) {
        caja.setOrden(buscarOrden(request.ordenId()));
        caja.setNombre(request.nombre());
        caja.setSerie(request.serie());
        caja.setFechaFabricacion(request.fechaFabricacion());
        caja.setCantidad(request.cantidad());
    }

    private OrdenProduccion buscarOrden(Integer ordenId) {
        return ordenRepository.findById(ordenId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "No existe una orden con id " + ordenId));
    }

    private CajaEmpaque buscarOFallar(Integer id) {
        return cajaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "No existe una caja de empaque con id " + id));
    }

    private CajaEmpaqueResponse aResponse(CajaEmpaque c) {
        return new CajaEmpaqueResponse(
                c.getId(),
                c.getOrden().getId(),
                c.getNombre(),
                c.getSerie(),
                c.getFechaFabricacion(),
                c.getCantidad()
        );
    }
}
