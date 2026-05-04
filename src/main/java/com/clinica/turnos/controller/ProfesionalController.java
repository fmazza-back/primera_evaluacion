package com.clinica.turnos.controller;

import com.clinica.turnos.model.Profesional;
import com.clinica.turnos.service.IProfesional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/profesionales")
public class ProfesionalController {

    private final IProfesional service;

    public ProfesionalController(IProfesional service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Profesional> crear(@RequestBody Profesional profesional) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.crearProfesional(profesional));
    }

    @GetMapping
    public ResponseEntity<List<Profesional>> listarPorEspecialidad(@RequestParam String especialidad) {
        return ResponseEntity.ok(service.obtenerProfesionalesPorEspecialidad(especialidad));
    }
}
