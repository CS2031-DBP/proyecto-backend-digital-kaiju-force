package com.dkf.ODAD.Ruta.dto;

import com.dkf.ODAD.Ubicacion.dto.UbicacionDTO;
import jakarta.persistence.Column;
import lombok.Data;

@Data
public class CreateRutaRequestDTO {
    private String fechaRuta;

    private String horaInicio;

    private String horaFin;

    private UbicacionDTO ubicacionInicioDTO;

    private UbicacionDTO ubicacionFinalDTO;

}
