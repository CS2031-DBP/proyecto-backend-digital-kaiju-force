package com.dkf.ODAD.Visita.dto;
import com.dkf.ODAD.Medico.dto.MedicoResponseDTO;
import jakarta.validation.Valid;
import lombok.Data;
import jakarta.validation.constraints.*;

@Data
public class VisitaResponseDTO {
    @NotNull
    private Long id;
    @NotNull
    private String fechaVisita;
    @NotNull
    private String horaVisita;
    @NotNull
    private String notas;
    @NotNull
    @Valid
    private MedicoResponseDTO medico;
}
