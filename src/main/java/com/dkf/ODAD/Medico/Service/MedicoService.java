package com.dkf.ODAD.Medico.Service;


import com.dkf.ODAD.Medico.Domain.Medico;
import com.dkf.ODAD.Medico.Infraestructure.MedicoRepository;
import com.dkf.ODAD.Medico.dto.MedicoResponseDTO;
import com.dkf.ODAD.Visita.Domain.Visita;
import com.dkf.ODAD.Visita.dto.VisitaResponseDTO;
import com.dkf.ODAD.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MedicoService {

    @Autowired
    private MedicoRepository medicoRepository;

    public List<Medico> medicos() {
        return medicoRepository.findAll();
    }

    public Medico MedicoById(Long id) {
        return medicoRepository.findById(id).orElse(null);
    }

    public MedicoResponseDTO getMedicoInfo(Long id) {
        Medico medico = medicoRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Medico no encontrado!!!"));

        MedicoResponseDTO response = new MedicoResponseDTO();
        response.setId(medico.getId());
        response.setNombre(medico.getNombre());
        response.setApellido(medico.getApellido());
        response.setEmail(medico.getEmail());
        response.setTelefono(medico.getTelefono());
        response.setAvgRating(medico.getAvgRating());
        response.setEspecialidad(medico.getEspecialidad());
        response.setTotalVisitas(medico.getVisitas().size());

        return response;
    }


}
