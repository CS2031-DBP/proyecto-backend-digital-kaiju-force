package com.dkf.ODAD.HistorialMedico.Infraestructure;

import com.dkf.ODAD.HistorialMedico.Domain.HistorialMedico;
import com.dkf.ODAD.Medico.Domain.Medico;
import com.dkf.ODAD.Paciente.Domain.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public interface HistorialMedicoRepository extends JpaRepository<HistorialMedico, Long> {
    List<HistorialMedico> findByPacienteId(Long pacienteId);
}
