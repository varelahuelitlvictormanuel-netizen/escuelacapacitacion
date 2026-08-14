package com.victor.escuela.controllers;

import com.victor.escuela.dto.cursos.CursoRequest;
import com.victor.escuela.dto.cursos.CursoResponse;
import com.victor.escuela.services.curso.CursoService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/curso")
public class CursoController extends CommonController<CursoRequest,CursoResponse, CursoService> {
    public CursoController(CursoService service){
        super(service);
    }
}
