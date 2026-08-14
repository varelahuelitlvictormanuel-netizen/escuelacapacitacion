package com.victor.escuela.entities;

import com.victor.escuela.utils.StringCustomUtils;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "AULAS")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Aula {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_AULA")
    private Long id;

    @Column(name = "NOMBRE", nullable = false, length = 100, unique = true)
    private String nombre;

    @Column(name = "CAPACIDAD", nullable = false)
    private Integer capacidad;

    public void validarDatos(String nombre) {
        StringCustomUtils.validarTamanio(nombre,1,50, "El nombre es requerido y debe tener 1 entre 50 caracteres");
    }
    public void Actualizar(String nombre) {
        validarDatos(nombre);
        this.nombre = nombre;
    }
}
