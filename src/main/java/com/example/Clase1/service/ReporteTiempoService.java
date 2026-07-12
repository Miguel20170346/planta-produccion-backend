package com.example.Clase1.service;

import com.example.Clase1.dto.ActividadResponse;
import com.example.Clase1.dto.ReporteTiempoRequest;
import com.example.Clase1.dto.ReporteTiempoResponse;
import com.example.Clase1.model.Actividad;
import com.example.Clase1.model.OrdenProduccion;
import com.example.Clase1.model.ReporteTiempo;
import com.example.Clase1.repository.ActividadRepository;
import com.example.Clase1.repository.OrdenProduccionRepository;
import com.example.Clase1.repository.ReporteTiempoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

/**
 * Logica de negocio de ReporteTiempo. Detalle de la orden.
 * Resuelve DOS relaciones: la orden (obligatoria) y la actividad (opcional).
 */
@Service
public class ReporteTiempoService {

    private final ReporteTiempoRepository reporteRepository;
    private final OrdenProduccionRepository ordenRepository;
    private final ActividadRepository actividadRepository;

    public ReporteTiempoService(ReporteTiempoRepository reporteRepository,
                                OrdenProduccionRepository ordenRepository,
                                ActividadRepository actividadRepository) {
        this.reporteRepository = reporteRepository;
        this.ordenRepository = ordenRepository;
        this.actividadRepository = actividadRepository;
    }

    @Transactional(readOnly = true)
    public List<ReporteTiempoResponse> listar(Integer ordenId) {
        List<ReporteTiempo> reportes = (ordenId == null)
                ? reporteRepository.findAll()
                : reporteRepository.findByOrdenId(ordenId);
        return reportes.stream().map(this::aResponse).toList();
    }

    @Transactional(readOnly = true)
    public ReporteTiempoResponse obtenerPorId(Integer id) {
        return aResponse(buscarOFallar(id));
    }

    @Transactional
    public ReporteTiempoResponse crear(ReporteTiempoRequest request) {
        ReporteTiempo reporte = new ReporteTiempo();
        aplicarDatos(reporte, request);
        return aResponse(reporteRepository.save(reporte));
    }

    @Transactional
    public ReporteTiempoResponse actualizar(Integer id, ReporteTiempoRequest request) {
        ReporteTiempo reporte = buscarOFallar(id);
        aplicarDatos(reporte, request);
        return aResponse(reporteRepository.save(reporte));
    }

    @Transactional
    public void eliminar(Integer id) {
        ReporteTiempo reporte = buscarOFallar(id);
        reporteRepository.delete(reporte);
    }

    // ---- Helpers privados ----

    private void aplicarDatos(ReporteTiempo reporte, ReporteTiempoRequest request) {
        validarHorario(request);
        reporte.setOrden(buscarOrden(request.ordenId()));
        reporte.setActividad(request.actividadId() == null ? null : buscarActividad(request.actividadId()));
        reporte.setHoraInicial(request.horaInicial());
        reporte.setHoraFinal(request.horaFinal());
    }

    /**
     * La hora final no puede ser IGUAL a la inicial (duracion cero).
     * Se permite que sea menor por si la actividad cruza la medianoche.
     */
    private void validarHorario(ReporteTiempoRequest request) {
        if (request.horaInicial() != null && request.horaFinal() != null
                && request.horaFinal().equals(request.horaInicial())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "La hora final no puede ser igual a la inicial (duracion cero).");
        }
    }

    private OrdenProduccion buscarOrden(Integer ordenId) {
        return ordenRepository.findById(ordenId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "No existe una orden con id " + ordenId));
    }

    private Actividad buscarActividad(Integer actividadId) {
        return actividadRepository.findById(actividadId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "No existe una actividad con id " + actividadId));
    }

    private ReporteTiempo buscarOFallar(Integer id) {
        return reporteRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "No existe un reporte de tiempo con id " + id));
    }

    private ReporteTiempoResponse aResponse(ReporteTiempo r) {
        Actividad act = r.getActividad();
        return new ReporteTiempoResponse(
                r.getId(),
                r.getOrden().getId(),
                act == null ? null : new ActividadResponse(act.getId(), act.getCodigo(), act.getDescripcion()),
                r.getHoraInicial(),
                r.getHoraFinal()
        );
    }
}
