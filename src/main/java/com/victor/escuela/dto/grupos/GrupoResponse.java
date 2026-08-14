package com.victor.escuela.dto.grupos;

import com.victor.escuela.dto.datos.DatosAula;
import com.victor.escuela.dto.datos.DatosCurso;
import com.victor.escuela.dto.datos.DatosMaestro;

import java.util.List;

public record GrupoResponse(
        Long id,
        DatosCurso curso,
        DatosMaestro maestro,
        DatosAula aula,
        List<String> horarios,
        String periodo
) {
}