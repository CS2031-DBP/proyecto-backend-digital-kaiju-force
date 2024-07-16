package com.dkf.ODAD.Ubicacion.Infraestructure;

import com.dkf.ODAD.Tratamiento.Domain.Tratamiento;
import com.dkf.ODAD.Ubicacion.Domain.Ubicacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UbicacionRepository extends JpaRepository<Ubicacion, Long> {
    Ubicacion findByUser_Id(Long pacienteId);
}
