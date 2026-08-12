package com.victor.escuela.mappers;

import com.victor.escuela.dto.cursos.CursoRequest;
import com.victor.escuela.dto.cursos.CursoResponse;
import com.victor.escuela.dto.datos.DatosCurso;
import com.victor.escuela.entities.Curso;
import org.springframework.stereotype.Component;

@Component
public class CursoMapper implements CommonMapper <CursoRequest, CursoResponse, Curso> {

    @Override
    public Curso requestAEntidad(CursoRequest request){
        if(request == null)return null;
        String descripcion = request.descripcion() != null
                ? request.descripcion().trim() : null;
        return Curso.builder()
                .nombre(request.nombre().trim())
                .descripcion(descripcion)
                .creditos(request.creditos())
                .build();
    }
    @Override
    public CursoResponse entidadAResponse(Curso entidad) {
        if (entidad == null) return null;
        String descricion = entidad.getDescripcion() == null
                ?"Sin descripcion" : entidad.getDescripcion();

        return new CursoResponse(
                entidad.getId(),
                entidad.getNombre(),
                descricion,
                entidad.getCreditos()
        );
    }
    public DatosCurso entidadADatosCurso(Curso entidad) {
        if (entidad == null) return null;
        String descricion = entidad.getDescripcion() == null
                ?"Sin descripcion" : entidad.getDescripcion();

        return new DatosCurso(
                entidad.getNombre(),
                descricion,
                entidad.getCreditos()
        );
    }

}

