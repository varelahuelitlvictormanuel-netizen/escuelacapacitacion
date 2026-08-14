package com.victor.escuela.dto.calificaciones;


import com.victor.escuela.dto.datos.DatosInscripcion;

import java.math.BigDecimal;

public record CalificacionResponse(

        Long id,
        DatosInscripcion inscripcion,
        BigDecimal calificacion,
        String fechaRegistro

) {
}
