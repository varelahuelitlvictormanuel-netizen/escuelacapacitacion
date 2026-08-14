package com.victor.escuela.repositories;

import com.victor.escuela.entities.Calificacion;
import com.victor.escuela.entities.Inscripcion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CalificacionRepository extends JpaRepository<Calificacion, Long> {

    boolean existsByInscripcionId(Long idInscripcion);

    boolean existsByInscripcionIdAndIdNot(Long idInscripcion, Long id);

}
