package com.dkf.ODAD.Paciente.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class NewPacienteInfoDTO {

    @Size(min = 3, max = 50)
    private String nombre;

    @Size(min = 3, max = 50)
    private String apellido;

    @Size(min = 9, max = 12)
    private String telefono;


    private String email;

    private Integer edad;

    @NotNull
    private String sexo;

}
