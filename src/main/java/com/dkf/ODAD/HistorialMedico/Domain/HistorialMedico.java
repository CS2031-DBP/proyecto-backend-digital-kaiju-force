package com.dkf.ODAD.HistorialMedico.Domain;

import com.dkf.ODAD.Paciente.Domain.Paciente;
import jakarta.persistence.*;

import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
public class HistorialMedico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private LocalDateTime fecha;

    @Column(nullable = false)
    private String descripcion;

    @ManyToOne
    @JoinColumn(name = "paciente_id", nullable = false)
    private Paciente paciente;
}