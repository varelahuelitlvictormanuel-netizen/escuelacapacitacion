package com.victor.escuela.controllers;

import com.victor.escuela.dto.aulas.AulaRequest;
import com.victor.escuela.dto.aulas.AulaResponse;
import com.victor.escuela.services.aulas.AulaService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/aulas")
public class AulaController extends CommonController<AulaRequest, AulaResponse, AulaService> {
    public AulaController (AulaService service){
        super(service);
    }
}
