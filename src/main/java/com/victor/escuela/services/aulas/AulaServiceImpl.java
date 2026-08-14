package com.victor.escuela.services.aulas;

import com.victor.escuela.dto.aulas.AulaRequest;
import com.victor.escuela.dto.aulas.AulaResponse;
import com.victor.escuela.entities.Aula;
import com.victor.escuela.exceptions.EntidadRelacionadaException;
import com.victor.escuela.mappers.AulaMapper;
import com.victor.escuela.repositories.AulaRepository;
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
public class AulaServiceImpl implements AulaService{
    private final AulaRepository aulaRepository;
    private final AulaMapper aulaMapper;
    private final GrupoRepository grupoRepository;
    @Override
    public List<AulaResponse> listar() {
        log.info("Listando todos las aulas");
        return aulaRepository.findAll().stream()
                .map(aulaMapper::entidadAResponse).toList();
    }

    @Override
    public AulaResponse obtenerPorId(Long id) {
        return aulaMapper.entidadAResponse(obtenerAula(id));
    }

    @Override
    public AulaResponse registrar(AulaRequest request) {
        log.info("Registrar nueva aula...");
        validarDatosUnicos(request);
        Aula aula = aulaMapper.requestAEntidad(request);
        aulaRepository.save(aula);
        log.info("Nueva aula {} registrar", aula.getNombre());
        return aulaMapper.entidadAResponse(aula);
    }

    @Override
    public AulaResponse actualizar(AulaRequest request, Long id) {
        Aula aula = obtenerAula(id);
        log.info("Actualizacion de aula con id: {}", id);
        validarCambiosUnicos(request, id);
        aula.Actualizar(
                request.nombre().trim()
        );
        log.info("Aula {} actulizada correctamente", aula.getNombre());
        return aulaMapper.entidadAResponse(aula);

    }

    @Override
    public void eliminar(Long id) {
        Aula aula = obtenerAula(id);
        log.info("Eliminando aula con id: {}", id);
        if (grupoRepository.existsByAulaId(id))
            throw new EntidadRelacionadaException(
                    "No se puede eliminar aulas por que tiene grupos asignados"
            );
        aulaRepository.delete(aula);

        log.info("Alumno con id {} eliminado", id);
    }
    //Meto para obtner id
    private Aula obtenerAula(Long id){
        return ServiceUtils.obtenerEntidadOException(aulaRepository,id, Aula.class);
    }
    //Validar para registrar
    private void validarDatosUnicos(AulaRequest request){
        log.info("Validar nombre unico...");
        if(aulaRepository.existsByNombreIgnoreCase(request.nombre().trim()))
            throw new IllegalArgumentException("Ya existe una aula registrada con el nombre:" + request.nombre());
    }
    //Validar para actualizar
    private void validarCambiosUnicos(AulaRequest request, Long id)     {
        log.info("Validar cambios unicos...");
        if (aulaRepository.existsByNombreIgnoreCaseAndIdNot(
                request.nombre().trim(), id)) {
            throw new IllegalArgumentException(
                    "Ya existe un aula registrada con el nombre: "
                            + request.nombre()
            );
        }
    }
}
