package com.victor.escuela.repositories;

import com.victor.escuela.entities.Alumno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AlumnosRepository extends JpaRepository<Alumno, Long> {
//Es interno a la base datos con el query permitiendo las funciones personalisadas a la base de datos
    @Query(nativeQuery = true, value = """
    SELECT GENERAR_MATRICULA(:nombre, :paterno, :materno) FROM DUAL
    """)
    String generarMatricula(
    @Param("nombre")String nombre,
    @Param("paterno")String apellidoPaterno,
    @Param("materno")String apellidoMaterno);

    @Query(nativeQuery = true, value = """
    SELECT GENERAR_EMAIL(:nombre, :paterno, :materno) FROM DUAL
    """)
    String generarEmail(
            @Param("nombre") String nombre,
            @Param("paterno") String apellidoPaterno,
            @Param("materno") String apellidoMaterno);
}
