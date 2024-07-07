package com.dkf.ODAD.Paciente.Infraestructure;

import com.dkf.ODAD.Paciente.Domain.Paciente;
import com.dkf.ODAD.Usuario.infrastructure.BaseUserRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface PacienteRepository extends BaseUserRepository<Paciente> {
    List<Paciente> findAllByMedicoId(Long id);
}
