package com.victor.escuela.enums;

import com.victor.escuela.exceptions.RecursoNoEncontradoException;
import com.victor.escuela.entities.Alumno;
import com.victor.escuela.utils.StringCustomUtils;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum DiaSemana {
    LUNES("Lunes"),
    MARTES("Martes"),
    MIERCOLES("Miercoles"),
    JUEVES("Jueves"),
    VIERNES("Viernes"),
    SABADO("Sabado");

    private final String descripcion;
    public static DiaSemana obtenerDiaSemanaPorDescripcion(String descripcion) {
        StringCustomUtils.validarNoVacio(descripcion, "La descripción es requerida");
        String descripcionNormalizada = StringCustomUtils.quitarAcentos(descripcion);
        for (DiaSemana diaSemana : values()) {
            if (StringCustomUtils.quitarAcentos(diaSemana.descripcion)
                    .equalsIgnoreCase(descripcionNormalizada)) {
                return diaSemana;
            }
        }
        throw new RecursoNoEncontradoException(
                "No existe un día de la semana con la descripción: " + descripcion
        );
    }
}
