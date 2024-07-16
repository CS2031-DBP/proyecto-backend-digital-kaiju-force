package com.dkf.ODAD.Medico.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class NewMedicoInfoDTO {
    @Size(min = 3, max = 50)
    private String nombre;

    @Size(min = 3, max = 50)
    private String apellido;

    @Size(min = 9, max = 12)
    private String telefono;

    @Size(min = 3, max = 30)
    private String especialidad;

    private String email;

    private Integer edad;

    private String sexo;

    private Float precio;

}
