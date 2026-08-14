package com.victor.escuela.services.grupo;

import com.victor.escuela.dto.cursos.CursoRequest;
import com.victor.escuela.dto.grupos.GrupoRequest;
import com.victor.escuela.dto.grupos.GrupoResponse;
import com.victor.escuela.entities.*;
import com.victor.escuela.exceptions.EntidadRelacionadaException;
import com.victor.escuela.mappers.GrupoMapper;
import com.victor.escuela.repositories.*;
import com.victor.escuela.services.curso.CursoService;
import com.victor.escuela.utils.ServiceUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class GrupoServiceImpl implements GrupoService {
    private final GrupoRepository grupoRepository;
    private final CursoRepository cursoRepository;
    private final AulaRepository aulaRepository;
    private final MaestroRepository maestroRepository;
    private final InscripcionRepository inscripcionRepository;
    private final GrupoMapper grupoMapper;

    @Override
    public List<GrupoResponse> listar() {
        log.info("Listando todos los grupos");
        return grupoRepository.findAll().stream()
                .map(grupoMapper::entidadAResponse).toList();
    }
    @Override
    public GrupoResponse obtenerPorId(Long id) {
        Grupo grupo = obtenerGrupo(id);
        return grupoMapper.entidadAResponse(grupo);
    }
    @Override
    public GrupoResponse registrar(GrupoRequest request) {
        log.info("Registrando nuevo grupo...");
        validarDatosUnicos(request);
        Curso curso = ServiceUtils.obtenerEntidadOException(
                cursoRepository,
                request.idCurso(),
                Curso.class
        );
        Maestro maestro = ServiceUtils.obtenerEntidadOException(
                maestroRepository,
                request.idMaestro(),
                Maestro.class
        );
        Aula aula = ServiceUtils.obtenerEntidadOException(
                aulaRepository,
                request.idAula(),
                Aula.class
        );
        Grupo grupo = new Grupo(
                curso,
                maestro,
                aula,
                request.periodo(),
                new ArrayList<>()
        );
        Grupo guardado = grupoRepository.save(grupo);
        log.info("Nuevo grupo {} registrado", guardado.getId());
        return grupoMapper.entidadAResponse(guardado);
    }

    @Override
    public GrupoResponse actualizar(GrupoRequest request, Long id) {
        Grupo grupo = obtenerGrupo(id);
        log.info("Actualizando grupo con id: {}", id);
        validarCambiosUnicos(request, id);
        Curso curso = ServiceUtils.obtenerEntidadOException(
                cursoRepository,
                request.idCurso(),
                Curso.class
        );
        Maestro maestro = ServiceUtils.obtenerEntidadOException(
                maestroRepository,
                request.idMaestro(),
                Maestro.class
        );
        Aula aula = ServiceUtils.obtenerEntidadOException(
                aulaRepository,
                request.idAula(),
                Aula.class
        );
        List<Horario> horarios = grupo.getHorarios();
       // Grupo actualizado = grupoRepository.save(grupo);
        grupo.actualizarGrupo(curso,maestro,aula, request.periodo(), horarios);
        log.info(
                "Grupo {} actualizado correctamente",
                grupo.getId()
        );
        return grupoMapper.entidadAResponse(grupo);
    }
    @Override
    public void eliminar(Long id) {
        if (inscripcionRepository.existsByGrupoId(id))
            throw new EntidadRelacionadaException("Nose se puede eliminar porque tiene una inscripcion asociada");
        Grupo grupo = obtenerGrupo(id);
        log.info(
                "Eliminando grupo con id: {}",
                id
        );
        grupoRepository.delete(grupo);
        log.info(
                "Grupo con id {} eliminado correctamente",
                id
        );
    }
    private Grupo obtenerGrupo(Long id) {
        return ServiceUtils.obtenerEntidadOException(
                grupoRepository,
                id,
                Grupo.class
        );
    }
    //Validando los unicos
    private void validarDatosUnicos(GrupoRequest request) {
        log.info("Validando grupo único...");
        if (grupoRepository.existsByCursoIdAndMaestroIdAndAulaIdAndPeriodo(
                request.idCurso(),
                request.idMaestro(),
                request.idAula(),
                request.periodo()
        )) {
            throw new IllegalArgumentException(
                    "Ya existe un grupo registrado con el mismo curso, maestro, aula y periodo"
            );
        }
    }
    private void validarCambiosUnicos(GrupoRequest request, Long id) {
        log.info("Validando cambios únicos...");
        if (grupoRepository.existsByCursoIdAndMaestroIdAndAulaIdAndPeriodoAndIdNot(
                request.idCurso(),
                request.idMaestro(),
                request.idAula(),
                request.periodo(),
                id
                )) {
            throw new IllegalArgumentException(
                    "Ya existe otro grupo registrado con el mismo curso, maestro, aula y periodo"
            );
        }
    }

}
