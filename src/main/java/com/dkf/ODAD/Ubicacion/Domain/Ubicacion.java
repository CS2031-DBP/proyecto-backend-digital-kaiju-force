package com.dkf.ODAD.Ubicacion.Domain;

import com.dkf.ODAD.Usuario.domain.Usuario;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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

    @Column
    private String descripcion;

    @JsonIgnoreProperties("ubicacion")
    @OneToOne(mappedBy = "ubicacion")
    private Usuario user;
}