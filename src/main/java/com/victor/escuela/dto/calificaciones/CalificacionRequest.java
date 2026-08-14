package com.victor.escuela.dto.calificaciones;

import java.math.BigDecimal;

public record CalificacionRequest(

        Long idInscripcion,
        BigDecimal calificacion

) {
}
