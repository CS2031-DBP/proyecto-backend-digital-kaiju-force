package com.dkf.ODAD.Medico.Domain;

import com.dkf.ODAD.Paciente.Domain.Paciente;
import com.dkf.ODAD.Ruta.Domain.Ruta;
import com.dkf.ODAD.Usuario.domain.Usuario;
import com.dkf.ODAD.Visita.Domain.Visita;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import lombok.*;

import java.util.ArrayList;
import java.util.List;



@Entity
@Data
public class Medico extends Usuario {

    @Column
    private String sexo;

    @Column
    private String especialidad;

    @JsonIgnoreProperties("medico")
    @OneToMany(mappedBy = "medico", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Visita> visitas = new ArrayList<>();

    @JsonIgnoreProperties("medico")
    @OneToMany(mappedBy = "medico", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ruta> rutas = new ArrayList<>();

    @JsonIgnoreProperties("medico")
    @OneToMany(mappedBy = "medico", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Paciente> pacientes;
}
