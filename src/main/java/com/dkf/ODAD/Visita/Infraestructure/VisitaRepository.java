package com.dkf.ODAD.Visita.Infraestructure;

import com.dkf.ODAD.Visita.Domain.Visita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VisitaRepository extends JpaRepository<Visita, Long> {
}
