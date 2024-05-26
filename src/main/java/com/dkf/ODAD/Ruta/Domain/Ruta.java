package com.dkf.ODAD.Ruta.Domain;

import com.dkf.ODAD.Medico.Domain.Medico;
import jakarta.persistence.*;

import lombok.*;

@Entity
@Data
public class Ruta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fechaRuta;

    @Column(nullable = false)
    private String horaInicio;

    @Column(nullable = false)
    private String horaFin;

    @ManyToOne
    @JoinColumn(name = "medico_id", nullable = false)
    private Medico medico;
}