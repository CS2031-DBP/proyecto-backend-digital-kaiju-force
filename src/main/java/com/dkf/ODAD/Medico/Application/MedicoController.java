package com.dkf.ODAD.Medico.Application;


import com.dkf.ODAD.Medico.Service.MedicoService;
import com.dkf.ODAD.Medico.dto.MedicoResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/Medico")
public class MedicoController {

    private final MedicoService medicoService;

    @Autowired
    private MedicoController(MedicoService medicoService) {this.medicoService = medicoService;}

    @GetMapping("/getMedicos")
    public ResponseEntity<MedicoResponseDTO> getMedico(@PathVariable Long id) {
        return ResponseEntity.ok(medicoService.getMedicoInfo(id));
    };

}
