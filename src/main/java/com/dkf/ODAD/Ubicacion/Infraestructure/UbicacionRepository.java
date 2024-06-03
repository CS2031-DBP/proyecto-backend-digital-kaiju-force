package com.dkf.ODAD.Ubicacion.Infraestructure;

import com.dkf.ODAD.Ubicacion.Domain.Ubicacion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UbicacionRepository extends JpaRepository<Ubicacion, Long> {
}
