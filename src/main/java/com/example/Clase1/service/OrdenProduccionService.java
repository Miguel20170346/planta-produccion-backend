package com.example.Clase1.service;

import com.example.Clase1.dto.MaquinaResponse;
import com.example.Clase1.dto.OperarioResponse;
import com.example.Clase1.dto.OrdenProduccionRequest;
import com.example.Clase1.dto.OrdenProduccionResponse;
import com.example.Clase1.dto.PaginaOrdenesResponse;
import com.example.Clase1.dto.TurnoResponse;
import com.example.Clase1.model.EstadoOrden;
import com.example.Clase1.model.Maquina;
import com.example.Clase1.model.Operario;
import com.example.Clase1.model.OrdenProduccion;
import com.example.Clase1.model.Turno;
import com.example.Clase1.repository.MaquinaRepository;
import com.example.Clase1.repository.OperarioRepository;
import com.example.Clase1.repository.OrdenProduccionRepository;
import com.example.Clase1.repository.TurnoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

/**
 * Logica de negocio de OrdenProduccion.
 * NOVEDAD: depende de VARIOS repositorios, porque necesita buscar la
 * maquina/operario/turno referenciados por sus IDs y enlazarlos.
 */
@Service
public class OrdenProduccionService {

    private final OrdenProduccionRepository ordenRepository;
    private final MaquinaRepository maquinaRepository;
    private final OperarioRepository operarioRepository;
    private final TurnoRepository turnoRepository;

    // Inyeccion por constructor: las 4 dependencias, explicitas.
    public OrdenProduccionService(OrdenProduccionRepository ordenRepository,
                                  MaquinaRepository maquinaRepository,
                                  OperarioRepository operarioRepository,
                                  TurnoRepository turnoRepository) {
        this.ordenRepository = ordenRepository;
        this.maquinaRepository = maquinaRepository;
        this.operarioRepository = operarioRepository;
        this.turnoRepository = turnoRepository;
    }

    @Transactional(readOnly = true)
    public List<OrdenProduccionResponse> listar() {
        return ordenRepository.findAll().stream()
                .map(this::aResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public OrdenProduccionResponse obtenerPorId(Integer id) {
        return aResponse(buscarOFallar(id));
    }

    /**
     * Devuelve UNA pagina de ordenes, con filtros opcionales.
     * Ordena por fecha (mas reciente primero) y, como desempate, por id.
     */
    @Transactional(readOnly = true)
    public PaginaOrdenesResponse listarPagina(int pagina, int tamano, String busqueda,
                                              Integer maquinaId, EstadoOrden estado,
                                              LocalDate desde, LocalDate hasta) {
        // Un texto vacio equivale a "sin filtro" (null).
        String filtro = (busqueda == null || busqueda.isBlank()) ? null : busqueda.trim();

        Pageable pageable = PageRequest.of(pagina, tamano,
                Sort.by(Sort.Order.desc("fecha"), Sort.Order.desc("id")));

        Page<OrdenProduccion> page = ordenRepository.buscar(filtro, maquinaId, estado, desde, hasta, pageable);

        List<OrdenProduccionResponse> contenido = page.getContent().stream()
                .map(this::aResponse)
                .toList();

        return new PaginaOrdenesResponse(
                contenido,
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages()
        );
    }

    @Transactional
    public OrdenProduccionResponse crear(OrdenProduccionRequest request) {
        if (ordenRepository.existsByOpCodigoIgnoreCase(request.opCodigo())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Ya existe una orden con el codigo '" + request.opCodigo() + "'");
        }
        OrdenProduccion orden = new OrdenProduccion();
        aplicarDatos(orden, request);
        return aResponse(ordenRepository.save(orden));
    }

    @Transactional
    public OrdenProduccionResponse actualizar(Integer id, OrdenProduccionRequest request) {
        OrdenProduccion orden = buscarOFallar(id);
        aplicarDatos(orden, request);
        return aResponse(ordenRepository.save(orden));
    }

    @Transactional
    public void eliminar(Integer id) {
        OrdenProduccion orden = buscarOFallar(id);
        ordenRepository.delete(orden);
    }

    // ---- Helpers privados ----

    /**
     * Copia los datos del request a la entidad, resolviendo las relaciones:
     * convierte cada id (maquinaId, etc.) en la entidad real de la base.
     */
    private void aplicarDatos(OrdenProduccion orden, OrdenProduccionRequest request) {
        validarHorario(request);

        orden.setFecha(request.fecha());
        orden.setDiseno(request.diseno());
        orden.setOpCodigo(request.opCodigo());
        orden.setHoraInicio(request.horaInicio());
        orden.setHoraFin(request.horaFin());

        // Estado: si viene en el request se usa; si no, se conserva el actual
        // (al crear, como no hay actual, arranca EN_PROCESO).
        if (request.estado() != null) {
            orden.setEstado(request.estado());
        } else if (orden.getEstado() == null) {
            orden.setEstado(EstadoOrden.EN_PROCESO);
        }

        orden.setMaquina(buscarMaquina(request.maquinaId()));
        orden.setOperario(request.operarioId() == null ? null : buscarOperario(request.operarioId()));
        orden.setTurno(request.turnoId() == null ? null : buscarTurno(request.turnoId()));
    }

    /**
     * Regla de negocio: la hora de fin no puede ser IGUAL a la de inicio
     * (la orden tendria duracion cero). Se permite que la fin sea menor,
     * porque una orden de turno nocturno puede cruzar la medianoche.
     */
    private void validarHorario(OrdenProduccionRequest request) {
        if (request.horaInicio() != null && request.horaFin() != null
                && request.horaFin().equals(request.horaInicio())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "La hora de fin no puede ser igual a la de inicio (la orden tendria duracion cero).");
        }
    }

    private Maquina buscarMaquina(Integer maquinaId) {
        return maquinaRepository.findById(maquinaId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "No existe una maquina con id " + maquinaId));
    }

    private Operario buscarOperario(Integer operarioId) {
        return operarioRepository.findById(operarioId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "No existe un operario con id " + operarioId));
    }

    private Turno buscarTurno(Integer turnoId) {
        return turnoRepository.findById(turnoId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "No existe un turno con id " + turnoId));
    }

    private OrdenProduccion buscarOFallar(Integer id) {
        return ordenRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "No existe una orden con id " + id));
    }

    /**
     * Convierte la entidad a DTO. Accedemos aqui a maquina/operario/turno
     * (que son LAZY) DENTRO de la transaccion, por eso se cargan sin error.
     */
    private OrdenProduccionResponse aResponse(OrdenProduccion o) {
        Maquina m = o.getMaquina();
        Operario op = o.getOperario();
        Turno t = o.getTurno();

        return new OrdenProduccionResponse(
                o.getId(),
                o.getFecha(),
                o.getDiseno(),
                o.getOpCodigo(),
                new MaquinaResponse(m.getId(), m.getNombre(), m.getTipo()),
                op == null ? null : new OperarioResponse(op.getId(), op.getNumero(), op.getNombre()),
                t == null ? null : new TurnoResponse(t.getId(), t.getNombre()),
                o.getHoraInicio(),
                o.getHoraFin(),
                o.getEstado(),
                o.getCreadoEn()
        );
    }
}
