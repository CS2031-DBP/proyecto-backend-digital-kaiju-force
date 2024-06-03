package com.dkf.ODAD.Ruta.Service;

import com.dkf.ODAD.Medico.Domain.Medico;
import com.dkf.ODAD.Medico.Infraestructure.MedicoRepository;
import com.dkf.ODAD.Medico.dto.NewMedicoInfoDTO;
import com.dkf.ODAD.Ruta.Domain.Ruta;
import com.dkf.ODAD.Ruta.Infraestructure.RutaRepository;
import com.dkf.ODAD.Ruta.dto.CreateRutaRequestDTO;
import com.dkf.ODAD.Ubicacion.Domain.Ubicacion;
import com.dkf.ODAD.Ubicacion.Infraestructure.UbicacionRepository;
import com.dkf.ODAD.auth.AuthHelper;
import com.dkf.ODAD.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Optional;

@Service
public class RutaService {

    @Autowired
    private RutaRepository rutaRepository;
    @Autowired
    private AuthHelper authHelper;
    @Autowired
    private MedicoRepository medicoRepository;
    @Autowired
    private UbicacionRepository ubicacionRepository;

    public List<Ruta> getAllRutas() {
        return rutaRepository.findAll();
    }

    public Optional<Ruta> getRutaById(Long id) {
        return rutaRepository.findById(id);
    }

    public String createRuta(CreateRutaRequestDTO rutaRequestDTO) {
        String userEmail = authHelper.getAuthenticatedUserEmail();
        Medico medicoViajero = medicoRepository
                .findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("Medico no encontrado!"));

        Ubicacion ubicacionInicio = ubicacionRepository.findById(rutaRequestDTO.getUbicacionInicioDTO().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Ubicación de inicio no encontrada!"));
        Ubicacion ubicacionFinal = ubicacionRepository.findById(rutaRequestDTO.getUbicacionFinalDTO().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Ubicación final no encontrada!"));


        Ruta ruta = new Ruta();
        ruta.setFechaRuta(rutaRequestDTO.getFechaRuta());
        ruta.setMedico(medicoViajero);
        ruta.setHoraInicio(rutaRequestDTO.getHoraInicio());
        ruta.setHoraFin(rutaRequestDTO.getHoraFin());
        ruta.setUbicacionInicio(ubicacionInicio);
        ruta.setUbicacionFin(ubicacionFinal);
        rutaRepository.save(ruta);
        return "Ruta creada!";
    }



    public void updateRutaInfo(Long id, Ruta rutaDetails) throws AccessDeniedException {
        String username = authHelper.getAuthenticatedUserEmail();

        Ruta ruta = rutaRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Medico no encontrado!"));

        ruta.setFechaRuta(rutaDetails.getFechaRuta());
        ruta.setMedico(rutaDetails.getMedico());
        ruta.setHoraFin(rutaDetails.getHoraFin());
        ruta.setHoraInicio(rutaDetails.getHoraInicio());
        ruta.setUbicacionInicio(rutaDetails.getUbicacionInicio());
        ruta.setUbicacionFin(rutaDetails.getUbicacionFin());

        rutaRepository.save(ruta);

    }

    public void deleteRuta(Long id) {
        rutaRepository
            .findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Visita no encontrada!"));
        rutaRepository.deleteById(id);
    }
}
