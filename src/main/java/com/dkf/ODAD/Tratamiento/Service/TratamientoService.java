package com.dkf.ODAD.Tratamiento.Service;

import com.dkf.ODAD.Paciente.Domain.Paciente;
import com.dkf.ODAD.Paciente.Infraestructure.PacienteRepository;
import com.dkf.ODAD.Tratamiento.Domain.Tratamiento;
import com.dkf.ODAD.Tratamiento.Infraestructure.TratamientoRepository;
import com.dkf.ODAD.Tratamiento.dto.TratamientoDTO;
import com.dkf.ODAD.auth.AuthHelper;
import com.dkf.ODAD.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TratamientoService {

    private final TratamientoRepository tratamientoRepository;
    private final PacienteRepository pacienteRepository;
    private final AuthHelper authHelper;

    @Autowired
    public TratamientoService(TratamientoRepository tratamientoRepository, PacienteRepository pacienteRepository, AuthHelper authHelper) {
        this.tratamientoRepository = tratamientoRepository;
        this.pacienteRepository = pacienteRepository;
        this.authHelper = authHelper;
    }

    public List<Tratamiento> findByPacienteID(Long pacienteID) {
        return tratamientoRepository.findByPaciente_Id(pacienteID);
    }

    public Tratamiento getTratamientoById(Long id) {
        return tratamientoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tratamiento no encontrado"));
    }

    public Tratamiento saveTratamiento(TratamientoDTO tratamientoDTO) {
        Paciente paciente = pacienteRepository.findById(tratamientoDTO.getPaciente_id())
                .orElseThrow(() -> new UsernameNotFoundException("Paciente no encontrado"));

        Tratamiento tratamiento = new Tratamiento();
        tratamiento.setNombreTratamiento(tratamientoDTO.getNombreTratamiento());
        tratamiento.setDescripcion(tratamientoDTO.getDescripcion());
        tratamiento.setPaciente(paciente);
        return tratamientoRepository.save(tratamiento);
    }

    public void deleteTratamiento(Long id) throws AccessDeniedException {
        String username = authHelper.getAuthenticatedUserEmail();
        Tratamiento tratamiento = tratamientoRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("Tratamiento no encontrado"));
        if (!tratamiento.getPaciente().getEmail().equals(username)) {
            throw new AccessDeniedException("User is not the owner of the resource.");
        }
        tratamientoRepository.deleteById(id);
    }

    public Tratamiento updateTratamiento(Long id, TratamientoDTO updatedTratamiento) {
        Tratamiento existingTratamiento = tratamientoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tratamiento no encontrado"));
        existingTratamiento.setNombreTratamiento(updatedTratamiento.getNombreTratamiento());
        existingTratamiento.setDescripcion(updatedTratamiento.getDescripcion());
        return tratamientoRepository.save(existingTratamiento);
    }
}
