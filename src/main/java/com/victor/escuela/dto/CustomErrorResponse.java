package com.victor.escuela.dto;

public record CustomErrorResponse(
        int codigo,
        String mensaje
) {
}
