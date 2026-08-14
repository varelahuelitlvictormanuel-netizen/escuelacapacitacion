package com.victor.escuela.mappers;

import com.victor.escuela.dto.datos.DatosAula;
import com.victor.escuela.dto.datos.DatosCurso;
import com.victor.escuela.dto.datos.DatosMaestro;
import com.victor.escuela.dto.grupos.GrupoRequest;
import com.victor.escuela.dto.grupos.GrupoResponse;
import com.victor.escuela.entities.Grupo;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GrupoMapper implements CommonMapper<GrupoRequest, GrupoResponse, Grupo>{
    @Override
    public Grupo requestAEntidad(GrupoRequest request) {
        return Grupo.builder()
                .periodo(request.periodo())
                .build();
    }
    @Override
    public GrupoResponse entidadAResponse(Grupo entidad) {

        if (entidad == null) {
            return null;
        }
        List<String> horario = odtenerListaHorario(entidad);
        return new GrupoResponse(
                entidad.getId(),
                entidadADatosCurso(entidad),
                entidadADatosMaestro(entidad),
                entidadADatosAula(entidad),
                horario,
                entidad.getPeriodo()
        );
    }

    private DatosCurso entidadADatosCurso(Grupo entidad){
        return  new DatosCurso(
                entidad.getCurso().getNombre(),
                entidad.getCurso().getDescripcion(),
                entidad.getCurso().getCreditos()
        );
    }

    private DatosMaestro entidadADatosMaestro(Grupo entidad){
        return new DatosMaestro(
                entidad.getMaestro().getNombre(),
                entidad.getMaestro().getEmail(),
                entidad.getMaestro().getTelefono()
        );
    }

    private DatosAula entidadADatosAula(Grupo entidad){
        return new DatosAula(
                entidad.getAula().getNombre(),
                entidad.getAula().getCapacidad()
        );
    }

    private List<String> odtenerListaHorario(Grupo entidad){
        if (entidad == null) return null;

        return entidad.getHorarios().stream().map(horario -> String.join(" ",
                horario.getDia().getDescripcion(),horario.getHoraInicio(),horario.getHoraFin())).toList();
    }
}
