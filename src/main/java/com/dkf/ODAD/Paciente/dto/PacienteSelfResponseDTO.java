package com.dkf.ODAD.Paciente.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PacienteSelfResponseDTO {
    @Size(min = 1, max = 50)
    private String nombre;
    @Size(min = 1, max = 50)
    private String apellido;
    private String email;
    @Size(min = 9, max = 12)
    private String telefono;
    private Integer edad;
    private String sexo;
    @DecimalMax("5")
    @DecimalMin("0")
    private Float avgRating;
    private int totalVisitas;

}
