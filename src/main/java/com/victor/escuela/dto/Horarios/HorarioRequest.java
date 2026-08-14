package com.victor.escuela.dto.Horarios;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public record HorarioRequest(
        @NotNull(message = "El ID del grupo es requerido")
        @Positive(message = "El ID del grupo debe ser positivo")
        Long idGrupo,

        @NotBlank(message = "El día de la semana es requerido")
        String dia,

        @NotBlank(message = "El nombre es requerido")
        @Pattern(regexp = "^([01][0-9]|2[0-3]):[0-5][0-9]$", message = "El horario inicio debe tener el formato HH:MM")
        String horaInicio,

        @NotBlank(message = "El nombre es requerido")
        @Pattern(regexp = "^([01][0-9]|2[0-3]):[0-5][0-9]$", message = "El horario fin debe tener el formato HH:MM")
        String horaFin
) {
}
