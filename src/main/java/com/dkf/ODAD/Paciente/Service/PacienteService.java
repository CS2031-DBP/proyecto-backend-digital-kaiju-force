package com.dkf.ODAD.Paciente.Service;

import com.dkf.ODAD.Medico.Domain.Medico;
import com.dkf.ODAD.Medico.Infraestructure.MedicoRepository;
import com.dkf.ODAD.Medico.dto.NewMedicoInfoDTO;
import com.dkf.ODAD.Paciente.Domain.Paciente;
import com.dkf.ODAD.Paciente.Infraestructure.PacienteRepository;
import com.dkf.ODAD.Paciente.dto.NewPacienteInfoDTO;
import com.dkf.ODAD.Paciente.dto.PacienteResponseDTO;
import com.dkf.ODAD.Paciente.dto.PacienteSelfResponseDTO;
import com.dkf.ODAD.Ubicacion.Domain.Ubicacion;
import com.dkf.ODAD.Ubicacion.dto.UbicacionDTO;
import com.dkf.ODAD.auth.AuthHelper;
import com.dkf.ODAD.exceptions.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class PacienteService {
    private final PacienteRepository pacienteRepository;
    private final AuthHelper authHelper;
    private final ModelMapper modelMapper;
    private final MedicoRepository medicoRepository;

    @Autowired
    public PacienteService(PacienteRepository pacienteRepository, ModelMapper modelMapper, AuthHelper authHelper, MedicoRepository medicoRepository) {
        this.pacienteRepository = pacienteRepository;
        this.modelMapper = modelMapper;
        this.authHelper = authHelper;
        this.medicoRepository = medicoRepository;
    }

    public List<Paciente> pacientes() {
        return pacienteRepository.findAll();
    }

    public List<Paciente> pacientesByMedico(Long id) {
        return pacienteRepository.findAllByMedicoId(id);
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
        response.setSexo(paciente.getSexo());

        return response;
    }

    public PacienteSelfResponseDTO getPacienteOwnInfo() {
        String username = authHelper.getAuthenticatedUserEmail();

        Paciente paciente = pacienteRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("Paciente no encontrado!"));
        System.out.println(paciente.getId());
        return modelMapper.map(paciente, PacienteSelfResponseDTO.class);

    }

    public Paciente savePaciente(Paciente paciente) {
        return pacienteRepository.save(paciente);
    }

    public void deletePaciente(Long id) throws AccessDeniedException {
        String username = authHelper.getAuthenticatedUserEmail();
        Paciente paciente = pacienteRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("Passenger not found"));
        if (!paciente.getEmail().equals(username)){
            throw new AccessDeniedException("User is not the owner of the resource.");
        }
        pacienteRepository.deleteById(id);
    }

    public Medico findMedicoByPacienteId(Long pacienteId) throws AccessDeniedException {
        String username = authHelper.getAuthenticatedUserEmail();
        Paciente paciente = pacienteRepository.findById(pacienteId).orElseThrow(()
                -> new UsernameNotFoundException("Passenger not found"));

        if (!paciente.getEmail().equals(username)){
            throw new AccessDeniedException("User is not the owner of the resource.");
        }
        Medico medico = paciente.getMedico();
        if (medico == null) {
            throw new ResourceNotFoundException("El paciente no tiene Medico asignado.");
        }
        return medico;
    }

    public void updateOrCreateMedico(Long medicoID) {
        String username = authHelper.getAuthenticatedUserEmail();
        Paciente paciente = pacienteRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("Paciente no encontrado!"));

        Medico medico = medicoRepository.findById(medicoID).orElseThrow(()
                -> new UsernameNotFoundException("Medico no encontrado!!"));

        paciente.setMedico(medico);

        pacienteRepository.save(paciente);
    }

    public void updateOrCreateUbicacion(UbicacionDTO ubicacionDTO) {
        String username = authHelper.getAuthenticatedUserEmail();
        Paciente paciente = pacienteRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("Paciente no encontrado!"));

        Ubicacion ubicacion = paciente.getUbicacion();

        if (ubicacion == null){
            ubicacion = new Ubicacion();
        }

        ubicacion.setLatitud(ubicacionDTO.getLatitud());
        ubicacion.setLongitud(ubicacionDTO.getLongitud());
        ubicacion.setDireccion(ubicacionDTO.getDireccion());
        ubicacion.setDescripcion(ubicacionDTO.getDescripcion());

        paciente.setUbicacion(ubicacion);
        pacienteRepository.save(paciente);
    }

    public void updatePacienteInfo(Long id, NewPacienteInfoDTO pacienteInfoDTO) throws java.nio.file.AccessDeniedException {
        String username = authHelper.getAuthenticatedUserEmail();

        Paciente paciente = pacienteRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Paciente no encontrado!"));

        if (!paciente.getEmail().equals(username)){
            throw new java.nio.file.AccessDeniedException("No tienes permiso para eso!");
        }
        if (StringUtils.hasText(pacienteInfoDTO.getNombre())) {
            paciente.setNombre(pacienteInfoDTO.getNombre());
        }
        if (StringUtils.hasText(pacienteInfoDTO.getApellido())) {
            paciente.setApellido(pacienteInfoDTO.getApellido());
        }
        if (StringUtils.hasText(pacienteInfoDTO.getSexo())) {
            paciente.setSexo(pacienteInfoDTO.getSexo());
        }
        if (StringUtils.hasText(pacienteInfoDTO.getTelefono())) {
            paciente.setTelefono(pacienteInfoDTO.getTelefono());
        }
        paciente.setEdad(pacienteInfoDTO.getEdad());
        pacienteRepository.save(paciente);

    }

}


