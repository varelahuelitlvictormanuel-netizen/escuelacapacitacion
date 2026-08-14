package com.victor.escuela.dto.aulas;

import jakarta.validation.constraints.*;

public record AulaRequest(
        @NotBlank(message = "El nombre es requerido")
        @Size(min = 1, max = 20, message = "El nombre debe de tener entre 1 y 20 caracteres")
        String nombre,
        @NotNull(message = "La capacidad es requerida")
        @Min(value = 1, message = "La capacidad debe ser mayor a 0")
        Integer capacidad

) {
}
