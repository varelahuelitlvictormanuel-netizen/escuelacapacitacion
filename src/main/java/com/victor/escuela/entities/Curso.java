package com.victor.escuela.entities;

import com.victor.escuela.utils.StringCustomUtils;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "CURSOS")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Curso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CURSO")
    private Long id;

    @Column(name = "NOMBRE", nullable = false, length = 100, unique = true)
    private String nombre;

    @Column(name = "DESCRIPCION", length = 200)
    private String descripcion;

    @Column(name = "CREDITOS", nullable = false)
    private Integer creditos;

    public void ValidarDatos(String nombre) {
        StringCustomUtils.validarTamanio(nombre,1,50, "El nombre es requerido y debe tener 1 entre 50 caracteres");
    }
    public void Actualizar(String nombre) {
        ValidarDatos(nombre);
        this.nombre = nombre;
    }
}
