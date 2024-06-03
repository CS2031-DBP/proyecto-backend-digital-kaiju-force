package com.dkf.ODAD.Paciente.Domain;

import com.dkf.ODAD.HistorialMedico.Domain.HistorialMedico;
import com.dkf.ODAD.Medico.Domain.Medico;
import com.dkf.ODAD.Tratamiento.Domain.Tratamiento;
import com.dkf.ODAD.Ubicacion.Domain.Ubicacion;
import com.dkf.ODAD.Usuario.domain.Usuario;
import com.dkf.ODAD.Visita.Domain.Visita;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Paciente extends Usuario {

    @Column
    private Integer edad;

    @Column
    private String sexo;

    @OneToMany(mappedBy = "paciente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Visita> visitas = new ArrayList<>();

    @OneToMany(mappedBy = "paciente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Tratamiento> tratamientos = new ArrayList<>();

    @OneToMany(mappedBy = "paciente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HistorialMedico> historialMedicoList = new ArrayList<>();

    @OneToOne(mappedBy = "paciente", cascade = CascadeType.ALL, orphanRemoval = true)
    private Ubicacion ubicacion;

    @ManyToOne
    @JoinColumn(name = "medico_id", nullable = false)
    private Medico medico;
}
