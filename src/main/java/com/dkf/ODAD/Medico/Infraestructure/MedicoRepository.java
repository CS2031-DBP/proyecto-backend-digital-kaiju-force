package com.dkf.ODAD.Medico.Infraestructure;

import com.dkf.ODAD.Medico.Domain.Medico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository

public interface MedicoRepository extends JpaRepository<Medico, Long> {
}
