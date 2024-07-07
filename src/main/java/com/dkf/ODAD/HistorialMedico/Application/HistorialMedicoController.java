package com.dkf.ODAD.HistorialMedico.Application;

import com.dkf.ODAD.HistorialMedico.Domain.HistorialMedico;
import com.dkf.ODAD.HistorialMedico.Service.HistorialMedicoService;
import com.dkf.ODAD.HistorialMedico.dto.HistorialMedicoDTO;
import com.dkf.ODAD.Tratamiento.Domain.Tratamiento;
import com.dkf.ODAD.Tratamiento.dto.TratamientoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PreAuthorize("hasRole('ROLE_MEDICO') or hasRole('ROLE_PACIENTE')")
    @GetMapping("/{id}")
    public ResponseEntity<HistorialMedico> getHistorialById(@PathVariable Long id) {
        HistorialMedico historialMedico = historialMedicoService.getHistorialByID(id);
        return new ResponseEntity<>(historialMedico, HttpStatus.OK);
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

    @PreAuthorize("hasRole('ROLE_MEDICO') or hasRole('ROLE_PACIENTE')")
    @PutMapping("/{id}")
    public ResponseEntity<HistorialMedico> updateHistorial(@PathVariable Long id, @RequestBody HistorialMedicoDTO historialMedicoDTO) {
        HistorialMedico updatedHistorial = historialMedicoService.updateHistorial(id, historialMedicoDTO);
        return new ResponseEntity<>(updatedHistorial, HttpStatus.OK);
    }

}
