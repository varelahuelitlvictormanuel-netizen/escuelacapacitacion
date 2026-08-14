package com.victor.escuela.services.alumnos;

import com.victor.escuela.dto.alumnos.AlumnoRequest;
import com.victor.escuela.dto.alumnos.AlumnoResponse;
import com.victor.escuela.entities.Alumno;
import com.victor.escuela.exceptions.EntidadRelacionadaException;
import com.victor.escuela.mappers.AlumnoMapper;
import com.victor.escuela.repositories.AlumnosRepository;
import com.victor.escuela.repositories.InscripcionRepository;
import com.victor.escuela.utils.ServiceUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class AlumnoServiceImpl implements AlumnoService{
    private final AlumnosRepository alumnosRepository;
    private final AlumnoMapper alumnoMapper;
    private final InscripcionRepository inscripcionRepository;
//Metodos
    @Override
    @Transactional(readOnly = true)
    public List<AlumnoResponse> listar() {
        log.info("Listando todos los alumnos");

        return alumnosRepository.findAll().stream()
                .map(alumnoMapper::entidadAResponse).toList();
    }
    @Transactional
    @Override
    public AlumnoResponse obtenerPorId(Long id) {
        return alumnoMapper.entidadAResponse(obtenerAlumno(id));
    }
    //Escritura
    @Override
    public AlumnoResponse registrar(AlumnoRequest request) {
        log.info("Registro nuevo alumno...");
        Alumno alumno = alumnoMapper.requestAEntidad(
                request,
                generareEmail(request),
                generarMatricula(request)
        );
        alumnosRepository.save(alumno);
        log.info("Nuevo alumno {} registrado correctamente", alumno.getNombre());

        return alumnoMapper.entidadAResponse(alumno);
    }

    @Override
    public AlumnoResponse actualizar(AlumnoRequest request, Long id) {
        Alumno alumno = obtenerAlumno(id);
        //log se parametriza {} siempre por un odjeto id
        log.info("Actulizando alumno con id: {}", id);
        if(alumno.cambioEnDatos(
                request.nombre().trim(),
                request.apellidoPaterno().trim(),
                request.apellidoMaterno().trim()
        )){
            alumno.actualizar(
                    request.nombre(),
                    request.apellidoPaterno(),
                    request.apellidoMaterno(),
                    generareEmail(request),
                    generarMatricula(request)
            );
            log.info("Datos academicos regenerados para el alumno con id {}", id);
        }
        return alumnoMapper.entidadAResponse(alumno);
    }
//Siempre validar y ver el recorrido antes de eleminar y no traer varios registros
    @Override
    public void eliminar(Long id) {
        Alumno alumno = obtenerAlumno(id);

        log.info("Eliminando alumno con id: {}", id);

        if (inscripcionRepository.existsByAlumnoId(id))
            throw new EntidadRelacionadaException(
                    "No se puede eliminar el alumno ya que tiene inscripcion asignadas"
            );
        alumnosRepository.delete(alumno);

        log.info("Alumno con id {} eliminado", id);
    }

    private Alumno obtenerAlumno(Long id){
        return ServiceUtils.obtenerEntidadOException(alumnosRepository, id, Alumno.class);
    }
    private String generarMatricula(AlumnoRequest request){
        log.info("Genrar Matricuala..");
        return alumnosRepository.generarMatricula(
                request.nombre().trim(),
                request.apellidoPaterno().trim(),
                request.apellidoMaterno().trim()
        );
    }
    private String generareEmail(AlumnoRequest request){
        log.info("Genrar Email..");
        return alumnosRepository.generarEmail(
                request.nombre().trim(),
                request.apellidoPaterno().trim(),
                request.apellidoMaterno().trim()
        );
    }

}
