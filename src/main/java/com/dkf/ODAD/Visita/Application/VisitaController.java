package com.dkf.ODAD.Visita.Application;


import com.dkf.ODAD.Paciente.Domain.Paciente;
import com.dkf.ODAD.Visita.Domain.Visita;
import com.dkf.ODAD.Visita.Service.VisitaService;
import com.dkf.ODAD.Visita.dto.VisitaRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/visita")
public class VisitaController {

    @Autowired
    private VisitaService visitaService;


    @GetMapping
    public ResponseEntity<List<Visita>> getVisitas() {
        List<Visita> visitas = visitaService.getAllVisitas();
        return new ResponseEntity<>(visitas, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Visita> getVisitaById(@PathVariable Long id) {
        Optional<Visita> visita = visitaService.getVisitaById(id);
        return visita.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('ROLE_MEDICO')")
    @PostMapping
    public ResponseEntity<Visita> crearVisita(@RequestBody VisitaRequestDTO visitaRequestDTO) {
        Visita nuevaVisita = visitaService.createVisita(visitaRequestDTO);
        return new ResponseEntity<>(nuevaVisita, HttpStatus.CREATED);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVisita(@PathVariable Long id) {
        visitaService.deleteVisita(id);
        return ResponseEntity.noContent().build();
    }
}
