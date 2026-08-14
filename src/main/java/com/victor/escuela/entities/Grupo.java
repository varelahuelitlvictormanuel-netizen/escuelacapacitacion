package com.victor.escuela.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "GRUPOS", uniqueConstraints = @UniqueConstraint(
        name = "UK_CU_MA_AU_PE",
        columnNames = {"ID_CURSO","ID_MAESTRO","ID_AULA", "PERIODO"}
))
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Grupo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_GRUPO")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_CURSO", nullable = false)
    private Curso curso;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_MAESTRO", nullable = false)
    private Maestro maestro;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_AULA", nullable = false)
    private Aula aula;

    @Column(name = "PERIODO", nullable = false)
    private String periodo;
    @Builder.Default
    @OneToMany(mappedBy = "grupo",fetch = FetchType.LAZY)
    private List<Horario> horarios = new ArrayList<>();

    public Grupo(Curso curso, Maestro maestro, Aula aula, String periodo, List<Horario> horarios) {
        this.curso = curso;
        this.maestro = maestro;
        this.aula = aula;
        this.periodo = periodo;
        this.horarios = horarios;
    }

    public void actualizarGrupo(Curso curso, Maestro maestro, Aula aula, String periodo, List<Horario> horarios) {
        this.curso = curso;
        this.maestro = maestro;
        this.aula = aula;
        this.periodo = periodo;
        this.horarios = horarios;
    }
}
