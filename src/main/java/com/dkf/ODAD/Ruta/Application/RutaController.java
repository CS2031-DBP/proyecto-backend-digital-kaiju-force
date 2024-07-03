package com.dkf.ODAD.Ruta.Application;

import com.dkf.ODAD.Ruta.Domain.Ruta;
import com.dkf.ODAD.Ruta.Service.RutaService;
import com.dkf.ODAD.Ruta.dto.CreateRutaRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ruta")
public class RutaController {

    @Autowired
    private RutaService rutaService;

    @PreAuthorize("hasRole('ROLE_MEDICO')")
    @PostMapping
    public ResponseEntity<Ruta> createRuta(@RequestBody CreateRutaRequestDTO rutaRequestDTO) {
        rutaService.createRuta(rutaRequestDTO);
        return ResponseEntity.ok().build();
    }
}