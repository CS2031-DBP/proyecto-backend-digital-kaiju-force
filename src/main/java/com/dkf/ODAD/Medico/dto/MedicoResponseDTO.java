package com.dkf.ODAD.Medico.dto;

import com.dkf.ODAD.Ruta.dto.RutaDTO;
import com.dkf.ODAD.Visita.dto.VisitaResponseDTO;
import jakarta.validation.Valid;
import lombok.Data;
import jakarta.validation.constraints.*;

import java.util.List;

@Data
public class MedicoResponseDTO {
    @NotNull
    private Long id;
    @NotNull
    private String nombre;
    @NotNull
    private String apellido;
    @NotNull
    private String email;
    @NotNull
    private String telefono;
    @NotNull
    private Float avgRating;
    @NotNull
    private String especialidad;

    private Integer totalVisitas;
}
