package com.dkf.ODAD.HistorialMedico.Application;

import com.dkf.ODAD.HistorialMedico.Domain.HistorialMedico;
import com.dkf.ODAD.HistorialMedico.Service.HistorialMedicoService;
import com.dkf.ODAD.HistorialMedico.dto.HistorialMedicoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/historial")
public class HistorialMedicoController {

    private final HistorialMedicoService historialMedicoService;

    @Autowired
    public HistorialMedicoController(HistorialMedicoService historialMedicoService) {
        this.historialMedicoService=historialMedicoService;
    }

    @GetMapping("/paciente/{pacienteId}")
    public List<HistorialMedico> getHistorialesMedicosByPacienteId(@PathVariable Long pacienteId) {
        return historialMedicoService.findAllByPacienteId(pacienteId);
    }

    @PostMapping
    public ResponseEntity<HistorialMedico> createHistorialMedico(@RequestBody HistorialMedicoDTO historialMedicoDTO) {
        historialMedicoService.createHistorialMedico(historialMedicoDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHistorialMedico(@PathVariable Long id) {
        historialMedicoService.deleteHistorialMedicoById(id);
        return ResponseEntity.noContent().build();
    }
}
