package com.clinica.turnos.controller;

import com.clinica.turnos.dto.RespuestaDTO;
import com.clinica.turnos.model.Profesional;
import com.clinica.turnos.service.IProfesional;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Controlador REST para la gestión de profesionales médicos
@RestController
@RequestMapping("/profesionales")
public class ProfesionalController {

    private static final Logger log = LoggerFactory.getLogger(ProfesionalController.class);

    // Spring inyecta la implementación de IProfesional registrada en el contexto (ProfesionalServiceImpl)
    @Autowired
    private IProfesional service;

    // POST /profesionales → crea un nuevo profesional
    @PostMapping
    public ResponseEntity<RespuestaDTO<Profesional>> crear(@Valid @RequestBody Profesional profesional) {
        log.info("Solicitud POST /profesionales");
        Profesional creado = service.crearProfesional(profesional);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new RespuestaDTO<>(201, "Profesional creado exitosamente", creado));
    }

    // GET /profesionales?especialidad=... → filtra profesionales por especialidad
    @GetMapping
    public ResponseEntity<RespuestaDTO<List<Profesional>>> listarPorEspecialidad(@RequestParam String especialidad) {
        log.info("Solicitud GET /profesionales?especialidad={}", especialidad);
        return ResponseEntity.ok(new RespuestaDTO<>(200, "OK", service.obtenerProfesionalesPorEspecialidad(especialidad)));
    }
}
