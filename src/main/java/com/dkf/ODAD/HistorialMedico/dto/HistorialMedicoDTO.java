package com.dkf.ODAD.HistorialMedico.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class HistorialMedicoDTO {
    @NotNull

    private LocalDateTime fecha;

    @NotNull
    @Size(min = 3, max = 500)
    private String descripcion;

    private Long paciente_id;

}
