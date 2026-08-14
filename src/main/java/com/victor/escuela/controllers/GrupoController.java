package com.victor.escuela.controllers;

import com.victor.escuela.dto.grupos.GrupoRequest;
import com.victor.escuela.dto.grupos.GrupoResponse;
import com.victor.escuela.services.grupo.GrupoService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/grupo")
public class GrupoController extends CommonController<GrupoRequest, GrupoResponse, GrupoService> {
    public GrupoController(GrupoService service){
        super(service);
    }
}
