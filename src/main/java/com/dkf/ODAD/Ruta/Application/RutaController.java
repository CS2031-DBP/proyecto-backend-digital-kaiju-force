package com.dkf.ODAD.Ruta.Application;

import com.dkf.ODAD.Ruta.Domain.Ruta;
import com.dkf.ODAD.Ruta.Service.RutaService;
import com.dkf.ODAD.Ruta.dto.CreateRutaRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rutas")
public class RutaController {

    @Autowired
    private RutaService rutaService;

    @PostMapping
    public ResponseEntity<Ruta> createRuta(@RequestBody CreateRutaRequestDTO rutaRequestDTO) {
        rutaService.createRuta(rutaRequestDTO);
        return ResponseEntity.ok().build();
    }
}