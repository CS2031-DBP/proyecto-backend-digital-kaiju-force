package com.dkf.ODAD.Visita.Domain;

import com.dkf.ODAD.Medico.Domain.Medico;
import com.dkf.ODAD.Paciente.Domain.Paciente;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import lombok.*;

@Entity
@Data
public class Visita {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fechaVisita;

    @Column(nullable = false)
    private String horaVisita;

    @JsonIgnoreProperties("visitas")
    @ManyToOne
    @JoinColumn(name = "medico_id", nullable = false)
    private Medico medico;

    @JsonIgnoreProperties("visitas")
    @ManyToOne
    @JoinColumn(name = "paciente_id", nullable = false)
    private Paciente paciente;

    @Column
    private String notas;
}