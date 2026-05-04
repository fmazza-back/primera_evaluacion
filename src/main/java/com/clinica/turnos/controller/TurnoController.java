package com.clinica.turnos.controller;

import com.clinica.turnos.model.Turno;
import com.clinica.turnos.service.ITurnos;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

// Controlador REST para la gestión de turnos médicos
@RestController
@RequestMapping("/turnos")
public class TurnoController {

    private final ITurnos service;

    public TurnoController(ITurnos service) {
        this.service = service;
    }

    // POST /turnos → registra un nuevo turno (valida paciente, profesional y duplicados)
    @PostMapping
    public ResponseEntity<Turno> crear(@RequestBody Turno turno) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.crearTurno(turno));
    }

    // GET /turnos → retorna todos los turnos registrados
    @GetMapping
    public ResponseEntity<List<Turno>> listar() {
        return ResponseEntity.ok(service.obtenerTurnos());
    }

    // GET /turnos/fecha/{fecha} → retorna turnos de una fecha específica (formato yyyy-MM-dd)
    @GetMapping("/fecha/{fecha}")
    public ResponseEntity<List<Turno>> listarPorFecha(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        return ResponseEntity.ok(service.obtenerTurnosPorFecha(fecha));
    }

    // DELETE /turnos/{id} → elimina un turno por su ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminarTurno(id);
        return ResponseEntity.noContent().build();
    }
}
