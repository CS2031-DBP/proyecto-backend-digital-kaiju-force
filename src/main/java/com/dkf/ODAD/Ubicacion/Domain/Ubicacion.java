package com.dkf.ODAD.Ubicacion.Domain;

import com.dkf.ODAD.Paciente.Domain.Paciente;
import jakarta.persistence.*;

import lombok.*;

@Entity
@Data
public class Ubicacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Double latitud;

    @Column(nullable = false)
    private Double longitud;

    @Column(nullable = false)
    private String direccion;

    @OneToOne
    @JoinColumn(name = "paciente_id", nullable = false)
    private Paciente paciente;
}