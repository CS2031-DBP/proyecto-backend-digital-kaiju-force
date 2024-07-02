package com.dkf.ODAD.Tratamiento.Application;

import com.dkf.ODAD.Tratamiento.Domain.Tratamiento;
import com.dkf.ODAD.Tratamiento.Service.TratamientoService;
import com.dkf.ODAD.Tratamiento.dto.TratamientoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Tratamiento")
public class TratamientoController {

    private final TratamientoService tratamientoService;

    @Autowired
    public TratamientoController(TratamientoService tratamientoService) {
        this.tratamientoService = tratamientoService;
    }

    @GetMapping("/getTratamientos")
    public ResponseEntity<List<Tratamiento>> getTratamientos() {
        List<Tratamiento> tratamientos = tratamientoService.getAllTratamientos();
        return new ResponseEntity<>(tratamientos, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_MEDICO') or hasRole('ROLE_PACIENTE')")
    @GetMapping("/{id}")
    public ResponseEntity<Tratamiento> getTratamientoById(@PathVariable Long id) {
        Tratamiento tratamiento = tratamientoService.getTratamientoById(id);
        return new ResponseEntity<>(tratamiento, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_MEDICO')")
    @PostMapping("/addTratamiento")
    public ResponseEntity<Tratamiento> addTratamiento(@RequestBody TratamientoDTO tratamientoDTO) {
        Tratamiento nuevoTratamiento = tratamientoService.saveTratamiento(tratamientoDTO);
        return new ResponseEntity<>(nuevoTratamiento, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ROLE_MEDICO')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTratamiento(@PathVariable Long id) throws AccessDeniedException {
        tratamientoService.deleteTratamiento(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("hasRole('ROLE_MEDICO') or hasRole('ROLE_PACIENTE')")
    @PutMapping("/{id}")
    public ResponseEntity<Tratamiento> updateTratamiento(@PathVariable Long id, @RequestBody TratamientoDTO tratamientoDTO) {
        Tratamiento updatedTratamiento = tratamientoService.updateTratamiento(id, tratamientoDTO);
        return new ResponseEntity<>(updatedTratamiento, HttpStatus.OK);
    }
}
