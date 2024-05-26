package com.dkf.ODAD.Paciente.Service;

import com.dkf.ODAD.Paciente.Domain.Paciente;
import com.dkf.ODAD.Paciente.Infraestructure.PacienteRepository;
import com.dkf.ODAD.Paciente.dto.PacienteResponseDTO;
import com.dkf.ODAD.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PacienteService {
    @Autowired
    private PacienteRepository pacienteRepository;

    public List<Paciente> pacientes() {
        return pacienteRepository.findAll();
    }

    public PacienteResponseDTO getPacienteInfo(Long id) {
        Paciente paciente = pacienteRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Paciente no encontrado!!!"));

        PacienteResponseDTO response = new PacienteResponseDTO();

        response.setId(paciente.getId());
        response.setNombre(paciente.getNombre());
        response.setApellido(paciente.getApellido());
        response.setEdad(paciente.getEdad());
        response.setEmail(paciente.getEmail());
        response.setSexo(paciente.getSexo());
        response.setTelefono(paciente.getTelefono());
        response.setTotalVisitas(paciente.getVisitas().size());

        return response;
    }

    public Paciente savePaciente(Paciente paciente) {
        return pacienteRepository.save(paciente);
    }

    public void deletePaciente(Long id) {
        pacienteRepository.deleteById(id);
    }


}


