package com.dkf.ODAD.Paciente.Infraestructure;

import com.dkf.ODAD.Paciente.Domain.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface PacienteRepository extends JpaRepository<Paciente, Long> {
}
