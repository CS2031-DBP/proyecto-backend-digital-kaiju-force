package com.dkf.ODAD.Medico.Application;


import com.dkf.ODAD.Medico.Domain.Medico;
import com.dkf.ODAD.Medico.Service.MedicoService;
import com.dkf.ODAD.Medico.dto.MedicoResponseDTO;
import com.dkf.ODAD.Medico.dto.NewMedicoInfoDTO;
import com.dkf.ODAD.Paciente.Domain.Paciente;
import com.dkf.ODAD.Ruta.Domain.Ruta;
import com.dkf.ODAD.Visita.Domain.Visita;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;


@RestController
@RequestMapping("/medico")
public class MedicoController {

    private final MedicoService medicoService;

    @Autowired
    public MedicoController(MedicoService medicoService) {this.medicoService = medicoService;}

    @GetMapping("/getMedicos")
    public ResponseEntity<List<Medico>> getMedicos() {
        List<Medico> medicos = medicoService.medicos();
        return new ResponseEntity<>(medicos, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_MEDICO')")
    @GetMapping("/{id}")
    public ResponseEntity<MedicoResponseDTO> getMedico(@PathVariable Long id) {
        return ResponseEntity.ok(medicoService.getMedicoInfo(id));
    };

    @PreAuthorize("hasRole('ROLE_MEDICO')")
    @GetMapping("/me")
    public ResponseEntity<MedicoResponseDTO> getMedico() {
        return ResponseEntity.ok(medicoService.getMedicoOwnInfo());
    };

    @PreAuthorize("hasRole('ROLE_MEDICO')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMedico(@PathVariable Long id) throws AccessDeniedException {
        medicoService.deleteMedico(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ROLE_MEDICO')")
    @PatchMapping("/{id}")
    public ResponseEntity<String> updateMedicoInfo(@PathVariable Long id, @RequestBody NewMedicoInfoDTO medicoInfoDTO) throws AccessDeniedException {
        medicoService.updateMedicoInfo(id, medicoInfoDTO);
        return ResponseEntity.ok("Medico info updated");
    }

    @GetMapping("/{Id}/visitas")
    public List<Visita> getVisitas(@PathVariable Long Id) {
        return medicoService.getVisitas(Id);
    }

    @GetMapping("/{Id}/rutas")
    public List<Ruta> getRutas(@PathVariable Long Id) {
        return medicoService.getRutas(Id);
    }

}
