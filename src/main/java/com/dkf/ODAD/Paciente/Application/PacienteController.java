package com.dkf.ODAD.Paciente.Application;


import com.dkf.ODAD.Medico.Domain.Medico;
import com.dkf.ODAD.Medico.Service.MedicoService;
import com.dkf.ODAD.Medico.dto.UpdateMedicoDTO;
import com.dkf.ODAD.Paciente.Domain.Paciente;
import com.dkf.ODAD.Paciente.Service.PacienteService;
import com.dkf.ODAD.Paciente.dto.PacienteResponseDTO;
import com.dkf.ODAD.Paciente.dto.PacienteSelfResponseDTO;
import com.dkf.ODAD.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping("/Paciente")
public class PacienteController {

    private final PacienteService pacienteService;

    @Autowired
    public PacienteController(PacienteService pacienteService) {this.pacienteService = pacienteService;}

    @PreAuthorize("hasRole('ROLE_PACIENTE')")
    @GetMapping("/me")
    public ResponseEntity<PacienteSelfResponseDTO> getPassenger() {
        return ResponseEntity.ok(pacienteService.getPacienteOwnInfo());
    }

    @GetMapping("/getPacientes")
    public ResponseEntity<List<Paciente>> getPacientes() {
        List<Paciente> pacientes = pacienteService.pacientes();
        return new ResponseEntity<>(pacientes, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_PACIENTE') or hasRole('ROLE_MEDICO')")
    @GetMapping("/{id}")
    public ResponseEntity<PacienteResponseDTO> getPacienteById(@PathVariable Long id) {
        return  ResponseEntity.ok(pacienteService.getPacienteInfo(id));
    }

    @PreAuthorize("hasRole('ROLE_MEDICO')")
    @PostMapping("/addPaciente")
    public ResponseEntity<Paciente> addPaciente(@RequestBody Paciente paciente) {
        Paciente nuevoPaciente = pacienteService.savePaciente(paciente);
        return new ResponseEntity<>(nuevoPaciente, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ROLE_PACIENTE')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePaciente(@PathVariable Long id) throws AccessDeniedException {
        pacienteService.deletePaciente(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PreAuthorize(("hasRole('ROLE_PACIENTE')"))
    @GetMapping("/medico/{paciente_id}")
    public ResponseEntity<?> findMedico(@PathVariable Long paciente_id)  throws  AccessDeniedException {
        Medico medico = pacienteService.findMedicoByPacienteId(paciente_id);
        return new ResponseEntity<>(medico, HttpStatus.OK);
    }

    @PreAuthorize(("hasRole('ROLE_PACIENTE')"))
    @PatchMapping("/medico/{paciente_id}")
    public ResponseEntity<?> findMedico(@PathVariable Long paciente_id, @RequestBody UpdateMedicoDTO medicoDTO)
            throws  AccessDeniedException {
        pacienteService.updateOrCreateMedico(paciente_id, medicoDTO.getNombre(), medicoDTO.getApellido(), medicoDTO.getCorreo());
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
