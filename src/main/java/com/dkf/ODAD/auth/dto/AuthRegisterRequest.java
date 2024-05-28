package com.dkf.ODAD.auth.dto;

import lombok.Data;


@Data
public class AuthRegisterRequest {
    String nombre;
    String apellido;
    String email;
    String password;
    String telefono;
    Boolean isMedico = false;
}
