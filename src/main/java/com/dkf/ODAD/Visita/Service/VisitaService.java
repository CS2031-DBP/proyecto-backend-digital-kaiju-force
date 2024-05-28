package com.dkf.ODAD.Visita.Service;

import com.dkf.ODAD.Visita.Domain.Visita;
import com.dkf.ODAD.Visita.Infraestructure.VisitaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VisitaService {

    @Autowired
    private VisitaRepository visitaRepository;

    public List<Visita> getAllVisitas() {
        return visitaRepository.findAll();
    }

    public Optional<Visita> getVisitaById(Long id) {
        return visitaRepository.findById(id);
    }

    public Visita createVisita(Visita visita) {
        return visitaRepository.save(visita);
    }

    public Visita updateVisita(Long id, Visita visitaDetails) {
        Optional<Visita> optionalVisita = visitaRepository.findById(id);
        if (optionalVisita.isPresent()) {
            Visita visita = optionalVisita.get();
            visita.setFechaVisita(visitaDetails.getFechaVisita());
            visita.setHoraVisita(visitaDetails.getHoraVisita());
            visita.setMedico(visitaDetails.getMedico());
            visita.setPaciente(visitaDetails.getPaciente());
            visita.setNotas(visitaDetails.getNotas());
            return visitaRepository.save(visita);
        } else {
            return null; // Or throw an exception
        }
    }

    public void deleteVisita(Long id) {
        visitaRepository.deleteById(id);
    }
}
