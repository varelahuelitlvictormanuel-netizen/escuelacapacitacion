package com.victor.escuela.mappers;

import com.victor.escuela.dto.alumnos.AlumnoRequest;
import com.victor.escuela.dto.alumnos.AlumnoResponse;
import com.victor.escuela.dto.datos.DatosCalificacion;
import com.victor.escuela.entities.Alumno;
import com.victor.escuela.utils.StringCustomUtils;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class AlumnoMapper implements CommonMapper<AlumnoRequest, AlumnoResponse, Alumno> {
    @Override
    public Alumno requestAEntidad(AlumnoRequest request) {
        if (request == null) return null;
        return Alumno.builder()
                .nombre(request.nombre().trim())
                .apellidoPaterno(request.apellidoPaterno().trim())
                .apellidoMaterno(request.apellidoMaterno().trim())
                .build();
    }
    //Para genrar automaticamenteno se puede pedir se implementa una sobrecarga recursividad lamando a si mismo
    public Alumno requestAEntidad(AlumnoRequest request, String email, String matricula){
        if (request == null) return null;
        //Odjeto(Alumno) y variable (alumno)
        Alumno alumno = requestAEntidad(request);
        alumno.asignarDatosAcademicos(email, matricula);
        return alumno;
    }

    @Override
    public AlumnoResponse entidadAResponse(Alumno entidad) {
        if (entidad == null) return null;
        //Para los null en caliicaciones
        List<DatosCalificacion> calificaciones = entidadDatosCalificacion(entidad);
        //Es una repuesta a lo solisitando incluyendo toda nuestra entidad
        return new AlumnoResponse(
                entidad.getId(),
                String.join(" ",
                        entidad.getNombre(),
                        entidad.getApellidoPaterno(),
                        entidad.getApellidoMaterno()),
                entidad.getEmail(),
                entidad.getMatricula(),
                StringCustomUtils.localDateAString(
                        entidad.getFechaIngreso()),
                //Alumno responde se cambia el object por datoscalificacion
                calificaciones,
                //BigDecimal.ZERO
                entidad.calcularPromedio()
                );
    }
    // Metodo aparte para la lista de inscripcion utilisando un ternario ?
    private List<DatosCalificacion> entidadDatosCalificacion(Alumno entidad){
        if (entidad == null || entidad.getInscripcions() == null || entidad.getInscripcions().isEmpty())
            return  List.of();
        return  entidad.getInscripcions().stream()
                .map(inscripcion -> new  DatosCalificacion(
                        inscripcion.getGrupo().getCurso().getNombre(),
                        inscripcion.getGrupo().getPeriodo(),
                        inscripcion.getCalificacion() != null
                        ? inscripcion.getCalificacion().getCalificacion()
                                :null
                )).toList();
    }
}
