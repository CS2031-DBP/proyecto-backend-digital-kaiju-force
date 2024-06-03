package com.dkf.ODAD.Paciente.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class PacienteSelfResponseDTO {
    @NotNull
    @Size(min = 1, max = 50)
    private String nombre;
    @NotNull
    @Size(min = 1, max = 50)
    private String apellido;
    @NotNull
    private String email;
    @NotNull
    @Size(min = 9, max = 12)
    private String telefono;
    @NotNull
    private Integer edad;
    @NotNull
    private String sexo;
    @NotNull
    @DecimalMax("5")
    @DecimalMin("0")
    private Float avgRating;
    private int totalVisitas;

}
