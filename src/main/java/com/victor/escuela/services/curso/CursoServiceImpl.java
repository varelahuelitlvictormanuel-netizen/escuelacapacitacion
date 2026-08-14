package com.victor.escuela.services.curso;

import com.victor.escuela.dto.cursos.CursoRequest;
import com.victor.escuela.dto.cursos.CursoResponse;
import com.victor.escuela.entities.Curso;
import com.victor.escuela.exceptions.EntidadRelacionadaException;
import com.victor.escuela.mappers.CursoMapper;
import com.victor.escuela.repositories.CursoRepository;
import com.victor.escuela.repositories.GrupoRepository;
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
public class CursoServiceImpl implements CursoService{
    private final CursoMapper cursoMapper;
    private final CursoRepository cursoRepository;
    private  final GrupoRepository grupoRepository;
    @Override
    public List<CursoResponse> listar() {
        log.info("Listando todos las aulas");
        return cursoRepository.findAll().stream()
                .map(cursoMapper::entidadAResponse).toList();
    }

    @Override
    public CursoResponse obtenerPorId(Long id) {
        return cursoMapper.entidadAResponse(obtenerCurso(id));
    }

    @Override
    public CursoResponse registrar(CursoRequest request) {
        log.info("Registrar nuevo curso...");
        validarDatosUnicos(request);
        Curso curso = cursoMapper.requestAEntidad(request);
        cursoRepository.save(curso);
        log.info("Nuevo curso {} registrar", curso.getNombre());
        return cursoMapper.entidadAResponse(curso);
    }

    @Override
    public CursoResponse actualizar(CursoRequest request, Long id) {
        Curso curso = obtenerCurso(id);
        log.info("Actualizacion del curso con id: {}", id);
        validarCambiosUnicos(request, id);
        curso.Actualizar(
                request.nombre().trim()
        );
        log.info("Curso {} actulizado correctamente", curso.getNombre());
        return cursoMapper.entidadAResponse(curso);
    }

    @Override
    public void eliminar(Long id) {
        Curso curso = obtenerCurso(id);
        log.info("Eliminando curso con id: {}", id);
        if (grupoRepository.existsByCursoId(id))
            throw new EntidadRelacionadaException(
                    "No se puede eliminar curso por que tiene grupos asignados"
            );
        cursoRepository.delete(curso);
        log.info("Alumno con id {} eliminado", id);
    }
    private Curso obtenerCurso(Long id){
        return ServiceUtils.obtenerEntidadOException(cursoRepository,id, Curso.class);
    }
    private void validarDatosUnicos(CursoRequest request){
        log.info("Validar nombre unico...");
        if(cursoRepository.existsByNombreIgnoreCase(request.nombre().trim()))
            throw new IllegalArgumentException("Ya existe una aula registrada con el nombre:" + request.nombre());
    }
    private void validarCambiosUnicos(CursoRequest request, Long id){
        log.info("Validar cambios unicos...");
        if (cursoRepository.existsByNombreIgnoreCaseAndIdNot(
                request.nombre().trim(), id)) {
            throw new IllegalArgumentException(
                    "Ya existe un curso registrada con el nombre: "
                            + request.nombre()
            );
        }
    }
}
