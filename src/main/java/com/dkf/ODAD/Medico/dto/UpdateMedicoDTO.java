package com.dkf.ODAD.Medico.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateMedicoDTO {
    @NotNull
    private String nombre;
    @NotNull
    private String apellido;
}
