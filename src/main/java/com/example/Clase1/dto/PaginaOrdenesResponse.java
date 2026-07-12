package com.example.Clase1.dto;

import java.util.List;

/**
 * DTO de salida para una PAGINA de ordenes.
 * Ademas del contenido (las ordenes de esta pagina) devuelve los datos que el
 * frontend necesita para pintar los controles: numero de pagina actual,
 * tamano de pagina, total de elementos y total de paginas.
 */
public record PaginaOrdenesResponse(
        List<OrdenProduccionResponse> contenido,
        int pagina,
        int tamano,
        long totalElementos,
        int totalPaginas
) {
}
