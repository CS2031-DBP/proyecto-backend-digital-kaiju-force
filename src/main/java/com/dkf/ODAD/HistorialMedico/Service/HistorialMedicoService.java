package com.dkf.ODAD.HistorialMedico.Service;

import com.dkf.ODAD.HistorialMedico.Domain.HistorialMedico;
import com.dkf.ODAD.HistorialMedico.Infraestructure.HistorialMedicoRepository;
import com.dkf.ODAD.HistorialMedico.dto.HistorialMedicoDTO;
import com.dkf.ODAD.Medico.Domain.Medico;
import com.dkf.ODAD.Medico.Infraestructure.MedicoRepository;
import com.dkf.ODAD.Paciente.Domain.Paciente;
import com.dkf.ODAD.Paciente.Infraestructure.PacienteRepository;
import com.dkf.ODAD.Paciente.Service.PacienteService;
import com.dkf.ODAD.auth.AuthHelper;
import com.dkf.ODAD.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistorialMedicoService {
    private final HistorialMedicoRepository historialMedicoRepository;
    private final PacienteRepository pacienteRepository;
    private final AuthHelper authHelper;
    private final MedicoRepository medicoRepository;
    private final PacienteService pacienteService;

    @Autowired
    public HistorialMedicoService(HistorialMedicoRepository historialMedicoRepository, PacienteRepository pacienteRepository, AuthHelper authHelper, MedicoRepository medicoRepository, PacienteService pacienteService) {
        this.historialMedicoRepository = historialMedicoRepository;
        this.pacienteRepository = pacienteRepository;
        this.authHelper = authHelper;
        this.medicoRepository = medicoRepository;
        this.pacienteService = pacienteService;
    }


    public List<HistorialMedico> findAllByPacienteId(Long pacienteId) {
        pacienteRepository
                .findById(pacienteId)
                .orElseThrow(() -> new UsernameNotFoundException("Paciente no encontrado!!!"));
        return historialMedicoRepository.findByPacienteId(pacienteId);
    }

    public void createHistorialMedico(HistorialMedicoDTO historialMedicoDTO) {
        Long pacienteId = historialMedicoDTO.getPaciente_id();
        Paciente paciente = pacienteRepository
                .findById(pacienteId)
                .orElseThrow(() -> new UsernameNotFoundException("Paciente no encontrado!!!"));
        HistorialMedico historialMedico = new HistorialMedico();

        historialMedico.setPaciente(paciente);
        historialMedico.setDescripcion(historialMedicoDTO.getDescripcion());
        historialMedico.setFecha(historialMedicoDTO.getFecha());
        historialMedicoRepository.save(historialMedico);
    }

    public void deleteHistorialMedicoById(Long id) {
        historialMedicoRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Historial Medico no encontrado!"));
        historialMedicoRepository.deleteById(id);
    }

    public void updateFechaDescripcion(Long pacienteId, HistorialMedicoDTO historialMedicoDTO) throws AccessDeniedException {
        String username = authHelper.getAuthenticatedUserEmail();

        Medico medico = pacienteService.findMedicoByPacienteId(pacienteId);

        if (!medico.getEmail().equals(username)){
            throw new AccessDeniedException("No tienes permiso para eso!");
        }

        HistorialMedico historialMedico = historialMedicoRepository
                .findById(pacienteId)
                .orElseThrow(() -> new ResourceNotFoundException("Historial Medico no encontrado!!!"));

        historialMedico.setFecha(historialMedicoDTO.getFecha());
        historialMedico.setDescripcion(historialMedicoDTO.getDescripcion());

    }

}
