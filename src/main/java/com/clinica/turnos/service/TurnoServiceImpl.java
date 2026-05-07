package com.clinica.turnos.service;

import com.clinica.turnos.dto.TurnoRequestDTO;
import com.clinica.turnos.exception.DatoInvalidoException;
import com.clinica.turnos.exception.RecursoNoEncontradoException;
import com.clinica.turnos.model.Paciente;
import com.clinica.turnos.model.Profesional;
import com.clinica.turnos.model.Turno;
import com.clinica.turnos.repository.PacienteRepository;
import com.clinica.turnos.repository.ProfesionalRepository;
import com.clinica.turnos.repository.TurnoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

// Implementación de la lógica de negocio para la gestión de turnos
@Service
public class TurnoServiceImpl implements ITurnos {

    private static final Logger log = LoggerFactory.getLogger(TurnoServiceImpl.class);

    // Spring inyecta automáticamente cada repositorio al iniciar el contexto de la aplicación
    @Autowired
    private TurnoRepository turnoRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private ProfesionalRepository profesionalRepository;

    @Override
    public Turno crearTurno(TurnoRequestDTO dto) {
        log.info("Intentando crear turno para pacienteId={} y profesionalId={}", dto.getPacienteId(), dto.getProfesionalId());

        // Verifica que el paciente exista en el sistema
        Paciente paciente = pacienteRepository.findById(dto.getPacienteId())
                .orElseThrow(() -> {
                    log.error("Paciente no encontrado con id: {}", dto.getPacienteId());
                    return new RecursoNoEncontradoException("Paciente no encontrado con id: " + dto.getPacienteId());
                });

        // Verifica que el profesional exista en el sistema
        Profesional profesional = profesionalRepository.findById(dto.getProfesionalId())
                .orElseThrow(() -> {
                    log.error("Profesional no encontrado con id: {}", dto.getProfesionalId());
                    return new RecursoNoEncontradoException("Profesional no encontrado con id: " + dto.getProfesionalId());
                });

        // Evita registrar un turno duplicado para el mismo paciente, profesional y fecha
        if (turnoRepository.existsByPacienteAndProfesionalAndFecha(paciente, profesional, dto.getFecha())) {
            log.warn("Turno duplicado para pacienteId={}, profesionalId={}, fecha={}", dto.getPacienteId(), dto.getProfesionalId(), dto.getFecha());
            throw new DatoInvalidoException("Ya existe un turno para ese paciente, profesional y fecha");
        }

        Turno turno = new Turno();
        turno.setPaciente(paciente);
        turno.setProfesional(profesional);
        turno.setFecha(dto.getFecha());

        Turno guardado = turnoRepository.save(turno);
        log.info("Turno creado exitosamente con id={}", guardado.getId());
        return guardado;
    }

    @Override
    public Turno obtenerTurnoPorId(Long id) {
        log.info("Buscando turno con id={}", id);
        return turnoRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Turno no encontrado con id: {}", id);
                    return new RecursoNoEncontradoException("Turno no encontrado con id: " + id);
                });
    }

    @Override
    public List<Turno> obtenerTurnos() {
        log.info("Listando todos los turnos");
        return turnoRepository.findAll();
    }

    @Override
    public List<Turno> obtenerTurnosPorFecha(LocalDate fecha) {
        log.info("Buscando turnos para la fecha={}", fecha);
        return turnoRepository.findByFecha(fecha);
    }

    @Override
    public List<Turno> obtenerTurnosPorRangoDeFechas(LocalDate desde, LocalDate hasta) {
        log.info("Buscando turnos entre {} y {}", desde, hasta);
        if (desde.isAfter(hasta)) {
            throw new DatoInvalidoException("La fecha 'desde' no puede ser posterior a 'hasta'");
        }
        return turnoRepository.findByFechaBetween(desde, hasta);
    }

    @Override
    public void eliminarTurno(Long id) {
        log.info("Eliminando turno con id={}", id);
        obtenerTurnoPorId(id);
        turnoRepository.deleteById(id);
        log.info("Turno eliminado con id={}", id);
    }
}
