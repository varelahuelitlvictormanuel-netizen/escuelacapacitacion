package com.victor.escuela.repositories;

import com.victor.escuela.entities.Maestro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MaestroRepository extends JpaRepository<Maestro, Long> {
    //existBy para JpaRepositori
    //IgnoreCase para resivir lo creado
    boolean existsByEmailIgnoreCase(String email);

    boolean existsByTelefono(String telefono);
    //Para resivir lo modificado
    boolean existsByEmailIgnoreCaseAndIdNot(String email , Long id);

    boolean existsByTelefonoAndIdNot(String nombre, Long id);
}
