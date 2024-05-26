package com.dkf.ODAD.Paciente.Application;


import com.dkf.ODAD.Medico.Service.MedicoService;
import com.dkf.ODAD.Paciente.Domain.Paciente;
import com.dkf.ODAD.Paciente.Service.PacienteService;
import com.dkf.ODAD.Paciente.dto.PacienteResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping("/Paciente")
public class PacienteController {

    private final PacienteService pacienteService;

    @Autowired
    private PacienteController(PacienteService pacienteService) {this.pacienteService = pacienteService;}

    @GetMapping("/getPacientes")
    public ResponseEntity<List<Paciente>> getPacientes() {
        List<Paciente> pacientes = pacienteService.pacientes();
        return new ResponseEntity<>(pacientes, HttpStatus.OK);
    }

    @GetMapping("/getPaciente/{id}")
    public ResponseEntity<PacienteResponseDTO> getPacienteById(@PathVariable Long id) {
        return  ResponseEntity.ok(pacienteService.getPacienteInfo(id));
    }

    @PostMapping("/addPaciente")
    public ResponseEntity<Paciente> addPaciente(@RequestBody Paciente paciente) {
        Paciente nuevoPaciente = pacienteService.savePaciente(paciente);
        return new ResponseEntity<>(nuevoPaciente, HttpStatus.CREATED);
    }

    @DeleteMapping("/deletePaciente/{id}")
    public ResponseEntity<Void> deletePaciente(@PathVariable Long id) {
        pacienteService.deletePaciente(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
