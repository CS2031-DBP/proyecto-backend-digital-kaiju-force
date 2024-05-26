package com.dkf.ODAD.Ruta.dto;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RutaDTO {
    @NotNull
    private Long id;
    @NotNull
    private String fechaRuta;
    @NotNull
    private String horaInicio;
    @NotNull
    private String horaFin;
}