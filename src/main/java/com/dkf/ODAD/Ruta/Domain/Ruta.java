package com.dkf.ODAD.Ruta.Domain;

import com.dkf.ODAD.Medico.Domain.Medico;
import com.dkf.ODAD.Ubicacion.Domain.Ubicacion;
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

    @OneToOne
    @JoinColumn(name = "ubicacion_inicio_id", nullable = false)
    private Ubicacion ubicacionInicio;

    @OneToOne
    @JoinColumn(name = "ubicacion_fin_id", nullable = false)
    private Ubicacion ubicacionFin;
}