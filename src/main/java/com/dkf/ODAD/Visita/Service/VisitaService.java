package com.dkf.ODAD.Visita.Service;

import com.dkf.ODAD.Visita.Domain.Visita;
import com.dkf.ODAD.Visita.Infraestructure.VisitaRepository;
import com.dkf.ODAD.auth.AuthHelper;
import com.dkf.ODAD.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Optional;

@Service
public class VisitaService {

    @Autowired
    private VisitaRepository visitaRepository;
    @Autowired
    private AuthHelper authHelper;

    public List<Visita> getAllVisitas() {
        return visitaRepository.findAll();
    }

    public Optional<Visita> getVisitaById(Long id) {
        return visitaRepository.findById(id);
    }

    public Visita createVisita(Visita visita) {
        return visitaRepository.save(visita);
    }

    public void updateVisitaInfo(Long id, Visita visitaDetails) throws AccessDeniedException {
        String username = authHelper.getAuthenticatedUserEmail();

        Visita visita = visitaRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Visita no encontrada!"));

        visita.setFechaVisita(visitaDetails.getFechaVisita());
        visita.setHoraVisita(visitaDetails.getHoraVisita());
        visita.setPaciente(visitaDetails.getPaciente());
        visita.setMedico(visitaDetails.getMedico());
        visita.setNotas(visitaDetails.getNotas());

        visitaRepository.save(visita);

    }

    public void deleteVisita(Long id) {
        visitaRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Visita no encontrada!"));

        visitaRepository.deleteById(id);
    }
}
