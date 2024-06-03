package com.dkf.ODAD.Tratamiento.Infraestructure;

import com.dkf.ODAD.Tratamiento.Domain.Tratamiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TratamientoRepository extends JpaRepository<Tratamiento, Long> {
}
