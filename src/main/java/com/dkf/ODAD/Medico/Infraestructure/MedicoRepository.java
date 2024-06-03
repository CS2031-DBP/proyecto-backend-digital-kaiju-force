package com.dkf.ODAD.Medico.Infraestructure;

import com.dkf.ODAD.Medico.Domain.Medico;
import com.dkf.ODAD.Usuario.infrastructure.BaseUserRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository

public interface MedicoRepository extends BaseUserRepository<Medico> {
    Optional<Medico> findByNombreAndApellido(String medicoNombre, String medicoApellido);
}
