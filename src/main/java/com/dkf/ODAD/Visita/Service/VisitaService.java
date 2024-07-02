package com.dkf.ODAD.Visita.Service;

import com.dkf.ODAD.Medico.Domain.Medico;
import com.dkf.ODAD.Medico.Infraestructure.MedicoRepository;
import com.dkf.ODAD.Paciente.Domain.Paciente;
import com.dkf.ODAD.Paciente.Infraestructure.PacienteRepository;
import com.dkf.ODAD.Tratamiento.Domain.Tratamiento;
import com.dkf.ODAD.Visita.Domain.Visita;
import com.dkf.ODAD.Visita.Infraestructure.VisitaRepository;
import com.dkf.ODAD.Visita.dto.VisitaRequestDTO;
import com.dkf.ODAD.auth.AuthHelper;
import com.dkf.ODAD.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VisitaService {

    @Autowired
    private VisitaRepository visitaRepository;
    @Autowired
    private PacienteRepository pacienteRepository;
    @Autowired
    private MedicoRepository medicoRepository;
    @Autowired
    private AuthHelper authHelper;

    public List<Visita> getAllVisitas() {
        return visitaRepository.findAll();
    }

    public Optional<Visita> getVisitaById(Long id) {
        return visitaRepository.findById(id);
    }

    public Visita createVisita(VisitaRequestDTO visitaDTO) {
        String username = authHelper.getAuthenticatedUserEmail();
        Medico medico  = medicoRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("Médico no encontrado!"));

        Paciente paciente = pacienteRepository.findById(visitaDTO.getPaciente_id())
                .orElseThrow(() -> new UsernameNotFoundException("Paciente no encontrado"));

        Visita visita = new Visita();
        visita.setHoraVisita(visitaDTO.getHoraVisita());
        visita.setFechaVisita(visitaDTO.getFechaVisita());
        visita.setPaciente(paciente);
        visita.setNotas(visitaDTO.getNotas());
        visita.setMedico(medico);
        return visitaRepository.save(visita);
    }


    public void deleteVisita(Long id) {
        visitaRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Visita no encontrada!"));

        visitaRepository.deleteById(id);
    }
}
