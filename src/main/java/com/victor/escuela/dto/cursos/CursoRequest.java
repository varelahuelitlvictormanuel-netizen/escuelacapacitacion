package com.victor.escuela.dto.cursos;

import jakarta.validation.constraints.*;

public record CursoRequest(
        @NotBlank(message = "El nombre es requerido")
        @Size(min = 1, max = 50, message = "El nombre debe de tener entre 1 y 50 caracteres")
        String nombre,
        @NotBlank(message = "La descripcion es requerido")
        @Size(min = 1, max = 200, message = "La descripcion debe de tener entre 1 y 200 caracteres")
        String descripcion,
        @NotNull(message = "La creditos son requeridos")
        @Min(value = 1, message = "La creditos deben ser mayores a 0")
        Integer creditos
) {
}
