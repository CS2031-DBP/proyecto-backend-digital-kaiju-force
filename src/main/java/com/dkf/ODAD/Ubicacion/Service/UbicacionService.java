package com.dkf.ODAD.Ubicacion.Service;

import com.dkf.ODAD.Medico.Domain.Medico;
import com.dkf.ODAD.Medico.Infraestructure.MedicoRepository;
import com.dkf.ODAD.Paciente.Domain.Paciente;
import com.dkf.ODAD.Paciente.Infraestructure.PacienteRepository;
import com.dkf.ODAD.Ubicacion.Domain.Ubicacion;
import com.dkf.ODAD.Ubicacion.Infraestructure.UbicacionRepository;
import com.dkf.ODAD.Ubicacion.dto.UbicacionDTO;
import com.dkf.ODAD.auth.AuthHelper;
import com.dkf.ODAD.exceptions.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UbicacionService {
    private final UbicacionRepository ubicacionRepository;
    private final ModelMapper modelMapper;
    private final AuthHelper authHelper;
    private final MedicoRepository medicoRepository;
    private final PacienteRepository pacienteRepository;


    @Autowired
    public UbicacionService(UbicacionRepository ubicacionRepository, ModelMapper modelMapper, AuthHelper authHelper, MedicoRepository medicoRepository, PacienteRepository pacienteRepository) {
        this.ubicacionRepository = ubicacionRepository;
        this.modelMapper = modelMapper;
        this.authHelper = authHelper;
        this.medicoRepository = medicoRepository;
        this.pacienteRepository = pacienteRepository;
    }

    public UbicacionDTO assignUbicacion(Long ubicacionID) {
        String userEmail = authHelper.getAuthenticatedUserEmail();
        Optional<Medico> optionalMedico = medicoRepository
                .findByEmail(userEmail);

        Optional<Paciente> optionalPaciente = pacienteRepository
                .findByEmail(userEmail);

        Ubicacion ubicacion = ubicacionRepository
                .findById(ubicacionID)
                .orElseThrow(() -> new ResourceNotFoundException("Ubicación no econtrada!"));

        if (optionalMedico.isPresent()) {
            Medico medico = optionalMedico.get();
            ubicacion.setMedico(medico);
        }else if (optionalPaciente.isPresent()) {
            Paciente paciente = optionalPaciente.get();
            ubicacion.setPaciente(paciente);
        } else {
            throw new ResourceNotFoundException("Usuario no encontrado!");
        }
        ubicacionRepository.save(ubicacion);

        UbicacionDTO response = new UbicacionDTO();
        response.setId(ubicacionID);
        response.setLatitud(ubicacion.getLatitud());
        response.setLongitud(ubicacion.getLongitud());
        response.setDireccion(ubicacion.getDireccion());

        return response;
    }

}
