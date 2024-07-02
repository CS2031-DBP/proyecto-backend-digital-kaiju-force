package com.dkf.ODAD.Tratamiento.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TratamientoDTO {

    @NotNull
    private String nombreTratamiento;
    @NotNull
    private String descripcion;
    @NotNull
    private Long paciente_id;
}