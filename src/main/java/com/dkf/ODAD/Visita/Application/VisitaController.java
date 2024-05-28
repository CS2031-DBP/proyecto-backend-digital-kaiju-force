package com.dkf.ODAD.Visita.Application;

import com.dkf.ODAD.Visita.Domain.Visita;
import com.dkf.ODAD.Visita.Service.VisitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/visitas")
public class VisitaController {

    @Autowired
    private VisitaService visitaService;

    @GetMapping
    public List<Visita> getAllVisitas() {
        return visitaService.getAllVisitas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Visita> getVisitaById(@PathVariable Long id) {
        Optional<Visita> visita = visitaService.getVisitaById(id);
        return visita.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Visita createVisita(@RequestBody Visita visita) {
        return visitaService.createVisita(visita);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Visita> updateVisita(@PathVariable Long id, @RequestBody Visita visitaDetails) {
        Visita updatedVisita = visitaService.updateVisita(id, visitaDetails);
        if (updatedVisita != null) {
            return ResponseEntity.ok(updatedVisita);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVisita(@PathVariable Long id) {
        visitaService.deleteVisita(id);
        return ResponseEntity.noContent().build();
    }
}
