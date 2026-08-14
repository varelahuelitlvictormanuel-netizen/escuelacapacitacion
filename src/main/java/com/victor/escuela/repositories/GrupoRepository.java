package com.victor.escuela.repositories;

import com.victor.escuela.entities.Grupo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GrupoRepository extends JpaRepository<Grupo, Long> {
     boolean existsByMaestroId(Long idMaestro);
     boolean existsByAulaId(Long idAula);
     boolean existsByCursoId(Long idCurso);
     boolean existsByPeriodo(String periodo);
     //Validar datos unicos
     boolean existsByCursoIdAndMaestroIdAndAulaIdAndPeriodo(
             Long idCurso,
             Long idMaestro,
             Long idAula,
             String periodo
     );

     //Validación de los cambios unicos
     boolean existsByCursoIdAndMaestroIdAndAulaIdAndPeriodoAndIdNot(
             Long idCurso,
             Long idMaestro,
             Long idAula,
             String periodo,
             Long id
     );


}
