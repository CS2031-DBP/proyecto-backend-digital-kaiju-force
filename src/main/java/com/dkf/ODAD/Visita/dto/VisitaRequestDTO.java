package com.dkf.ODAD.Visita.dto;

import com.dkf.ODAD.Ubicacion.Domain.Ubicacion;
import lombok.Data;

@Data
public class VisitaRequestDTO {

    private String fechaVisita;

    private String horaVisita;

    private Long paciente_id;

    private String notas;
}
