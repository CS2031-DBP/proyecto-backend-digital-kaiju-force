package com.dkf.ODAD.HistorialMedico.Domain;

import com.dkf.ODAD.Paciente.Domain.Paciente;
import jakarta.persistence.*;

import lombok.*;

@Entity
@Data
public class HistorialMedico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fecha;

    @Column(nullable = false)
    private String descripcion;

    @ManyToOne
    @JoinColumn(name = "paciente_id", nullable = false)
    private Paciente paciente;
}