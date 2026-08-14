package com.victor.escuela.dto.inscripciones;

import com.victor.escuela.dto.datos.DatosAlumno;
import com.victor.escuela.dto.datos.DatosGrupo;

import java.math.BigDecimal;

public record InscripcionResponse(

        Long id,
        DatosAlumno alumno,
        DatosGrupo grupo,
        BigDecimal calificacion,
        String fechaInscripcion

) {
}
