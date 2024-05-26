package com.dkf.ODAD.Medico.Domain;

import com.dkf.ODAD.Paciente.Domain.Paciente;
import com.dkf.ODAD.Ruta.Domain.Ruta;
import com.dkf.ODAD.Usuario.domain.Usuario;
import com.dkf.ODAD.Visita.Domain.Visita;
import jakarta.persistence.*;

import lombok.*;

import java.util.ArrayList;
import java.util.List;



@Entity
@Data
public class Medico extends Usuario {

    @Column(nullable = false)
    private String sexo;

    @Column(nullable = false)
    private String especialidad;

    @Column(nullable = false, unique = true)
    private String correo;

    @OneToMany(mappedBy = "medico", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Visita> visitas = new ArrayList<>();

    @OneToMany(mappedBy = "medico", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ruta> rutas = new ArrayList<>();

}
