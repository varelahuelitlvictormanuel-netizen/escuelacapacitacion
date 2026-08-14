package com.victor.escuela.mappers;
import com.victor.escuela.dto.datos.DatosGrupo;
import com.victor.escuela.dto.Horarios.HorarioRequest;
import com.victor.escuela.dto.Horarios.HorarioResponse;
import com.victor.escuela.entities.Grupo;
import com.victor.escuela.entities.Horario;
import com.victor.escuela.enums.DiaSemana;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class HorarioMapper implements CommonMapper<HorarioRequest, HorarioResponse, Horario>{
    @Override
    public Horario requestAEntidad(HorarioRequest request) {

        if (request == null) return  null;

        return Horario.builder()
                .horaInicio(request.horaInicio())
                .horaFin(request.horaFin())
                .build();
    }

    public Horario requestAEntidad(HorarioRequest request, Grupo grupo, DiaSemana dia) {

        if (request == null) return  null;

        Horario horario = requestAEntidad(request);
        horario.asignarValores(grupo, dia);

        return horario;
    }

    @Override
    public HorarioResponse entidadAResponse(Horario entidad) {

        if (entidad == null) return null;

        DatosGrupo datosGrupo = entidadADatosGrupo(entidad.getGrupo());
        String horarioFormato = formatearHorario(entidad);
        return new HorarioResponse(
                entidad.getId(),
                datosGrupo,
                horarioFormato
        );
    }

    private DatosGrupo entidadADatosGrupo(Grupo entidad) {
        if (entidad == null || entidad.getCurso().getNombre() == null
                || entidad.getMaestro().getNombre() == null
                || entidad.getAula().getNombre() == null
                || entidad.getPeriodo() == null) return null;

        return new DatosGrupo(
                entidad.getCurso().getNombre(),
                entidad.getMaestro().getNombre(),
                entidad.getAula().getNombre(),
                entidad.getPeriodo()
        );
    }

    private String formatearHorario(Horario entidad) {
        if (entidad == null || entidad.getDia() == null) return null;

        String dia = entidad.getDia().getDescripcion();
        return String.format("%s %s %s", dia, entidad.getHoraInicio(), entidad.getHoraFin());
    }
}
