package com.dkf.ODAD.Ruta.dto;

import com.dkf.ODAD.Ubicacion.Domain.Ubicacion;
import lombok.Data;

@Data
public class CreateRutaRequestDTO {

    private String fechaRuta;

    private String horaInicio;

    private String horaFin;

    private Ubicacion ubicacionInicio;

    private Ubicacion ubicacionFinal;


}
