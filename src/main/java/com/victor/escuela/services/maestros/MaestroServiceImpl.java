package com.victor.escuela.services.maestros;

import com.victor.escuela.dto.maestros.MaestroRequest;
import com.victor.escuela.dto.maestros.MaestroResponse;
import com.victor.escuela.entities.Maestro;
import com.victor.escuela.exceptions.EntidadRelacionadaException;
import com.victor.escuela.mappers.MaestroMapper;
import com.victor.escuela.repositories.GrupoRepository;
import com.victor.escuela.repositories.MaestroRepository;
import com.victor.escuela.services.CrudService;
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
public class MaestroServiceImpl implements MaestroService {
    private  final MaestroMapper maestroMapper;
    private final MaestroRepository maestroRepository;
    private final GrupoRepository grupoRepository;
    @Override
    public List<MaestroResponse> listar() {
        log.info("Listando todos los maestros");
        return maestroRepository.findAll().stream()
                .map(maestroMapper::entidadAResponse).toList();
    }

    @Override
    public MaestroResponse obtenerPorId(Long id)
    {
        return maestroMapper.entidadAResponse(obtenerMaestro(id));
    }

    @Override
    public MaestroResponse registrar(MaestroRequest request) {
        log.info("Registrar nuevo maestro...");
        validarDatosUnicos(request);
        Maestro maestro = maestroMapper.requestAEntidad(request);
        maestroRepository.save(maestro);
        log.info("Nuevo maestro {} registrar", maestro.getNombre());
        return maestroMapper.entidadAResponse(maestro);
    }
    @Override
    public MaestroResponse actualizar(MaestroRequest request, Long id) {
        Maestro maestro = obtenerMaestro(id);
        log.info("Actualizacion de maestro con id: {}", id);
        validarCambiosUnicos(request, id);
        maestro.actualizar(
                request.nombre(),
                request.apellidoPaterno(),
                request.apellidoMaterno(),
                request.email(),
                request.telefono()
        );
        log.info("Maestro {} actulizado correctamente",maestro.getNombre());
        return maestroMapper.entidadAResponse(maestro);
    }

    @Override
    public void eliminar(Long id) {
        Maestro maestro = obtenerMaestro(id);
        log.info("Eliminando maestro con id: {}", id);

        if (grupoRepository.existsByMaestroId(id))
            throw new EntidadRelacionadaException(
                    "No se puede eliminar el maestro ya que tiene grupos asignadas"
            );
        maestroRepository.delete(maestro);
        log.info("Maestro con id {} eliminado", id);
    }
    private Maestro obtenerMaestro(Long id){
        return ServiceUtils.obtenerEntidadOException(maestroRepository,id,Maestro.class);
    }
    private void validarDatosUnicos(MaestroRequest request){
        log.info("Validar email unico...");
        if(maestroRepository.existsByEmailIgnoreCase(request.email().trim()))
            throw new IllegalArgumentException("Ya existe un maestro registrado con el email:" + request.email());
        log.info("Validar telefono unico...");
        if(maestroRepository.existsByTelefono(request.telefono().trim()))
            throw new IllegalArgumentException("Ya existe un maestro registrado con el telefono:" + request.telefono());
    }
    private void validarCambiosUnicos(MaestroRequest request, Long id){
        log.info("Validando cambio en email...");
        if(maestroRepository.existsByEmailIgnoreCaseAndIdNot(request.email().trim(), id))
            throw new IllegalArgumentException("Ya existe un maestro registrado con el email:" + request.email());
        log.info("Validar telefono unico...");
        if(maestroRepository.existsByTelefonoAndIdNot(request.telefono().trim(),id))
            throw new IllegalArgumentException("Ya existe un maestro registrado con el telefono:" + request.telefono());
    }



}
