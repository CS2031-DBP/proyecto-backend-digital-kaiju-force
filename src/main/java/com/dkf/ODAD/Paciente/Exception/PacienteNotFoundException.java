package com.dkf.ODAD.Paciente.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Paciente no encontrado")

    public class PacienteNotFoundException extends RuntimeException {
        public PacienteNotFoundException(String message) {super(message);}
}