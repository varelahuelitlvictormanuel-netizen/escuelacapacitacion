package com.victor.escuela.controllers;

import com.victor.escuela.dto.calificaciones.CalificacionRequest;
import com.victor.escuela.dto.calificaciones.CalificacionResponse;
import com.victor.escuela.services.calificaciones.CalificacionService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/calificaciones")
public class CalificacionesController extends CommonController<CalificacionRequest, CalificacionResponse, CalificacionService> {
    public CalificacionesController(CalificacionService service) {
        super(service);
    }
}
