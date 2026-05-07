package com.clinica.turnos.controller;

import com.clinica.turnos.dto.RespuestaDTO;
import com.clinica.turnos.dto.TurnoRequestDTO;
import com.clinica.turnos.model.Turno;
import com.clinica.turnos.service.ITurnos;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    private static final Logger log = LoggerFactory.getLogger(TurnoController.class);

    // Spring inyecta la implementación de ITurnos registrada en el contexto (TurnoServiceImpl)
    @Autowired
    private ITurnos service;

    // POST /turnos → registra un nuevo turno (valida paciente, profesional y duplicados)
    @PostMapping
    public ResponseEntity<RespuestaDTO<Turno>> crear(@Valid @RequestBody TurnoRequestDTO dto) {
        log.info("Solicitud POST /turnos");
        Turno creado = service.crearTurno(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new RespuestaDTO<>(201, "Turno registrado exitosamente", creado));
    }

    // GET /turnos → retorna todos los turnos, con filtro opcional por rango de fechas
    @GetMapping
    public ResponseEntity<RespuestaDTO<List<Turno>>> listar(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate desde,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate hasta) {
        List<Turno> turnos;
        if (desde != null && hasta != null) {
            log.info("Solicitud GET /turnos?desde={}&hasta={}", desde, hasta);
            turnos = service.obtenerTurnosPorRangoDeFechas(desde, hasta);
        } else {
            log.info("Solicitud GET /turnos");
            turnos = service.obtenerTurnos();
        }
        return ResponseEntity.ok(new RespuestaDTO<>(200, "OK", turnos));
    }

    // GET /turnos/fecha/{fecha} → retorna turnos de una fecha específica (formato yyyy-MM-dd)
    @GetMapping("/fecha/{fecha}")
    public ResponseEntity<RespuestaDTO<List<Turno>>> listarPorFecha(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        log.info("Solicitud GET /turnos/fecha/{}", fecha);
        return ResponseEntity.ok(new RespuestaDTO<>(200, "OK", service.obtenerTurnosPorFecha(fecha)));
    }

    // DELETE /turnos/{id} → elimina un turno por su ID
    @DeleteMapping("/{id}")
    public ResponseEntity<RespuestaDTO<Void>> eliminar(@PathVariable Long id) {
        log.info("Solicitud DELETE /turnos/{}", id);
        service.eliminarTurno(id);
        return ResponseEntity.ok(new RespuestaDTO<>(200, "Turno eliminado exitosamente", null));
    }
}
