package com.clinica.turnos.controller;

import com.clinica.turnos.dto.RespuestaDTO;
import com.clinica.turnos.model.Paciente;
import com.clinica.turnos.service.IPaciente;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Controlador REST para la gestión de pacientes
@RestController
@RequestMapping("/pacientes")
public class PacienteController {

    private static final Logger log = LoggerFactory.getLogger(PacienteController.class);

    private final IPaciente service;

    public PacienteController(IPaciente service) {
        this.service = service;
    }

    // POST /pacientes → crea un nuevo paciente
    @PostMapping
    public ResponseEntity<RespuestaDTO<Paciente>> crear(@Valid @RequestBody Paciente paciente) {
        log.info("Solicitud POST /pacientes");
        Paciente creado = service.crearPaciente(paciente);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new RespuestaDTO<>(201, "Paciente creado exitosamente", creado));
    }

    // GET /pacientes/{id} → retorna un paciente por su ID
    @GetMapping("/{id}")
    public ResponseEntity<RespuestaDTO<Paciente>> obtener(@PathVariable Long id) {
        log.info("Solicitud GET /pacientes/{}", id);
        return ResponseEntity.ok(new RespuestaDTO<>(200, "OK", service.obtenerPacientePorId(id)));
    }

    // GET /pacientes → retorna todos los pacientes
    @GetMapping
    public ResponseEntity<RespuestaDTO<List<Paciente>>> listar() {
        log.info("Solicitud GET /pacientes");
        return ResponseEntity.ok(new RespuestaDTO<>(200, "OK", service.obtenerPacientes()));
    }

    // DELETE /pacientes/{id} → elimina un paciente por su ID
    @DeleteMapping("/{id}")
    public ResponseEntity<RespuestaDTO<Void>> eliminar(@PathVariable Long id) {
        log.info("Solicitud DELETE /pacientes/{}", id);
        service.eliminarPaciente(id);
        return ResponseEntity.ok(new RespuestaDTO<>(200, "Paciente eliminado exitosamente", null));
    }
}
