package com.dkf.ODAD.Ubicacion.dto;

import lombok.Data;

@Data
public class UbicacionDTO {
    private Double latitud;
    private Double longitud;
    private String direccion;
    private String descripcion;
}