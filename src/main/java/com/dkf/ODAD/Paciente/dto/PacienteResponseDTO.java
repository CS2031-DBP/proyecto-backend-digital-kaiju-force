package com.dkf.ODAD.Paciente.dto;
import com.dkf.ODAD.Visita.dto.VisitaResponseDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Data
public class PacienteResponseDTO {
    @NotNull
    private Long id;
    @NotNull
    private String nombre;
    @NotNull
    private String apellido;
    @NotNull
    private Integer edad;
    @NotNull
    private String sexo;
}
