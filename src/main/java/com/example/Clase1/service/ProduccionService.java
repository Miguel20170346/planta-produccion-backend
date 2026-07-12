package com.example.Clase1.service;

import com.example.Clase1.dto.ProduccionRequest;
import com.example.Clase1.dto.ProduccionResponse;
import com.example.Clase1.model.OrdenProduccion;
import com.example.Clase1.model.Produccion;
import com.example.Clase1.repository.OrdenProduccionRepository;
import com.example.Clase1.repository.ProduccionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

/**
 * Logica de negocio de Produccion (1-a-1 con la orden).
 * Regla nueva: una orden solo puede tener UN bloque de produccion.
 */
@Service
public class ProduccionService {

    private final ProduccionRepository produccionRepository;
    private final OrdenProduccionRepository ordenRepository;

    public ProduccionService(ProduccionRepository produccionRepository,
                             OrdenProduccionRepository ordenRepository) {
        this.produccionRepository = produccionRepository;
        this.ordenRepository = ordenRepository;
    }

    @Transactional(readOnly = true)
    public List<ProduccionResponse> listar(Integer ordenId) {
        List<Produccion> lista = (ordenId == null)
                ? produccionRepository.findAll()
                : produccionRepository.findByOrdenId(ordenId).map(List::of).orElseGet(List::of);
        return lista.stream().map(this::aResponse).toList();
    }

    @Transactional(readOnly = true)
    public ProduccionResponse obtenerPorId(Integer id) {
        return aResponse(buscarOFallar(id));
    }

    @Transactional
    public ProduccionResponse crear(ProduccionRequest request) {
        // Regla 1-a-1: la orden no puede tener ya un bloque de produccion.
        if (produccionRepository.existsByOrdenId(request.ordenId())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "La orden " + request.ordenId() + " ya tiene un registro de produccion");
        }
        Produccion produccion = new Produccion();
        aplicarDatos(produccion, request);
        return aResponse(produccionRepository.save(produccion));
    }

    @Transactional
    public ProduccionResponse actualizar(Integer id, ProduccionRequest request) {
        Produccion produccion = buscarOFallar(id);
        aplicarDatos(produccion, request);
        return aResponse(produccionRepository.save(produccion));
    }

    @Transactional
    public void eliminar(Integer id) {
        Produccion produccion = buscarOFallar(id);
        produccionRepository.delete(produccion);
    }

    // ---- Helpers privados ----

    private void aplicarDatos(Produccion produccion, ProduccionRequest request) {
        produccion.setOrden(buscarOrden(request.ordenId()));
        produccion.setBolsasProducidas(request.bolsasProducidas());
        produccion.setProductoConformeKg(request.productoConformeKg());
        produccion.setDesperdicioPapelKg(request.desperdicioPapelKg());
        produccion.setDesperdicioRefilKg(request.desperdicioRefilKg());
        produccion.setMetrosLineales(request.metrosLineales());
        produccion.setCantidadPorBulto(request.cantidadPorBulto());
        produccion.setTotalCantidadProducida(request.totalCantidadProducida());
        produccion.setObservaciones(request.observaciones());
    }

    private OrdenProduccion buscarOrden(Integer ordenId) {
        return ordenRepository.findById(ordenId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "No existe una orden con id " + ordenId));
    }

    private Produccion buscarOFallar(Integer id) {
        return produccionRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "No existe un registro de produccion con id " + id));
    }

    private ProduccionResponse aResponse(Produccion p) {
        return new ProduccionResponse(
                p.getId(),
                p.getOrden().getId(),
                p.getBolsasProducidas(),
                p.getProductoConformeKg(),
                p.getDesperdicioPapelKg(),
                p.getDesperdicioRefilKg(),
                p.getMetrosLineales(),
                p.getCantidadPorBulto(),
                p.getTotalCantidadProducida(),
                p.getObservaciones()
        );
    }
}
