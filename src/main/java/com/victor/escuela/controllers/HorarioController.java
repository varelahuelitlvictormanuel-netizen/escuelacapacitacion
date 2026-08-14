package com.victor.escuela.controllers;

import com.victor.escuela.dto.Horarios.HorarioRequest;
import com.victor.escuela.dto.Horarios.HorarioResponse;
import com.victor.escuela.services.Horario.HorarioService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/horarios")
public class HorarioController extends CommonController<HorarioRequest, HorarioResponse, HorarioService>{
    public HorarioController(HorarioService service) {
        super(service);
    }
}
