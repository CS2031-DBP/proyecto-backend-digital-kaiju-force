package com.dkf.ODAD.Medico.Service;


import com.dkf.ODAD.Medico.Domain.Medico;
import com.dkf.ODAD.Medico.Infraestructure.MedicoRepository;
import com.dkf.ODAD.Medico.dto.MedicoResponseDTO;
import com.dkf.ODAD.Medico.dto.NewMedicoInfoDTO;
import com.dkf.ODAD.Ruta.Domain.Ruta;
import com.dkf.ODAD.Ruta.Infraestructure.RutaRepository;
import com.dkf.ODAD.Ruta.Service.RutaService;
import com.dkf.ODAD.Visita.Domain.Visita;
import com.dkf.ODAD.Visita.Service.VisitaService;
import com.dkf.ODAD.auth.AuthHelper;
import com.dkf.ODAD.exceptions.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Optional;

@Service
public class MedicoService {


    private final MedicoRepository medicoRepository;
    private final VisitaService visitaService;
    private final RutaService rutaService;
    private final AuthHelper authHelper;
    private final ModelMapper modelMapper;
    private final RutaRepository rutaRepository;

    @Autowired
    public MedicoService(MedicoRepository medicoRepository, VisitaService visitaService, RutaService rutaService,
                         ModelMapper modelMapper, AuthHelper authHelper, RutaRepository rutaRepository) {
        this.medicoRepository = medicoRepository;
        this.visitaService = visitaService;
        this.rutaService = rutaService;
        this.modelMapper = modelMapper;
        this.authHelper = authHelper;
        this.rutaRepository = rutaRepository;
    }



    public List<Medico> medicos() {
        return medicoRepository.findAll();
    }

    public Medico MedicoById(Long id) {
        return medicoRepository.findById(id).orElse(null);
    }

    public MedicoResponseDTO getMedicoInfo(Long id) {
        Medico medico = medicoRepository
                .findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("Medico no encontrado!!!"));

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

    public MedicoResponseDTO getMedicoOwnInfo() {
        String username = authHelper.getAuthenticatedUserEmail();

        Medico medico = medicoRepository
                .findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Driver not found"));

        return getMedicoInfo(medico.getId());
    }

    public void deleteMedico(Long id) throws AccessDeniedException {
        String username = authHelper.getAuthenticatedUserEmail();

        Optional<Medico> optionalMedico = medicoRepository.findById(id);

        Medico medico = medicoRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("Medico no encontrado!"));
        if (!medico.getEmail().equals(username)){
            throw new AccessDeniedException("No tienes permiso para eso!");
        }

        medicoRepository.deleteById(id);
    }

    public void updateMedicoInfo(Long id, NewMedicoInfoDTO newMedicoInfoDTO) throws AccessDeniedException {
        String username = authHelper.getAuthenticatedUserEmail();

        Medico medico = medicoRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Medico no encontrado!"));

        if (!medico.getEmail().equals(username)){
            throw new AccessDeniedException("No tienes permiso para eso!");
        }
        medico.setNombre(newMedicoInfoDTO.getNombre());
        medico.setApellido(newMedicoInfoDTO.getApellido());
        medico.setTelefono(newMedicoInfoDTO.getTelefono());
        medico.setEspecialidad(newMedicoInfoDTO.getEspecialidad());
        medico.setCorreo(newMedicoInfoDTO.getCorreo());
        medico.setSexo(newMedicoInfoDTO.getSexo());

        medicoRepository.save(medico);

    }

    public List<Visita> getVisitas(Long medicoId) {
        Medico medico = medicoRepository.findById(medicoId)
                .orElseThrow(() -> new ResourceNotFoundException("Medico no encontrado!"));
        return medico.getVisitas();
    }

    public Visita addVisita(Long medicoId, Visita visita) throws AccessDeniedException {
        String username = authHelper.getAuthenticatedUserEmail();

        Medico medico = medicoRepository.findById(medicoId)
                .orElseThrow(() -> new ResourceNotFoundException("Medico no encontrado!"));

        if (!medico.getEmail().equals(username)){
            throw new AccessDeniedException("No tienes permiso para eso!");
        }
        visita.setMedico(medico);
        return visitaService.createVisita(visita);
    }

    public List<Ruta> getRutas(Long medicoId) {
        Medico medico = medicoRepository.findById(medicoId)
                .orElseThrow(() -> new ResourceNotFoundException("Medico no encontrado!"));
        return medico.getRutas();
    }

    public Ruta addRuta(Long medicoId, Ruta ruta) {
        Medico medico = medicoRepository.findById(medicoId)
                .orElseThrow(() -> new ResourceNotFoundException("Medico no encontrado!"));
        ruta.setMedico(medico);
        return rutaRepository.save(ruta);
    }


}
