package com.clinica.turnos.controller;

import com.clinica.turnos.model.Paciente;
import com.clinica.turnos.service.IPaciente;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {

    private final IPaciente service;

    public PacienteController(IPaciente service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Paciente> crear(@RequestBody Paciente paciente) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.crearPaciente(paciente));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Paciente> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtenerPacientePorId(id));
    }

    @GetMapping
    public ResponseEntity<List<Paciente>> listar() {
        return ResponseEntity.ok(service.obtenerPacientes());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminarPaciente(id);
        return ResponseEntity.noContent().build();
    }
}
