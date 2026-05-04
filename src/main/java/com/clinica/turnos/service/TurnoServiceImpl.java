package com.clinica.turnos.service;

import com.clinica.turnos.exception.DatoInvalidoException;
import com.clinica.turnos.exception.RecursoNoEncontradoException;
import com.clinica.turnos.model.Paciente;
import com.clinica.turnos.model.Profesional;
import com.clinica.turnos.model.Turno;
import com.clinica.turnos.repository.PacienteRepository;
import com.clinica.turnos.repository.ProfesionalRepository;
import com.clinica.turnos.repository.TurnoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

// Implementación de la lógica de negocio para la gestión de turnos
@Service
public class TurnoServiceImpl implements ITurnos {

    private final TurnoRepository turnoRepository;
    private final PacienteRepository pacienteRepository;
    private final ProfesionalRepository profesionalRepository;

    public TurnoServiceImpl(TurnoRepository turnoRepository,
                             PacienteRepository pacienteRepository,
                             ProfesionalRepository profesionalRepository) {
        this.turnoRepository = turnoRepository;
        this.pacienteRepository = pacienteRepository;
        this.profesionalRepository = profesionalRepository;
    }

    @Override
    public Turno crearTurno(Turno turno) {
        // Valida que el turno tenga paciente y profesional informados
        if (turno.getPaciente() == null || turno.getPaciente().getId() == null) {
            throw new DatoInvalidoException("El turno debe tener un paciente válido");
        }
        if (turno.getProfesional() == null || turno.getProfesional().getId() == null) {
            throw new DatoInvalidoException("El turno debe tener un profesional válido");
        }

        // Verifica que el paciente exista en el sistema
        Paciente paciente = pacienteRepository.findById(turno.getPaciente().getId())
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "Paciente no encontrado con id: " + turno.getPaciente().getId()));

        // Verifica que el profesional exista en el sistema
        Profesional profesional = profesionalRepository.findById(turno.getProfesional().getId())
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "Profesional no encontrado con id: " + turno.getProfesional().getId()));

        // Evita registrar un turno duplicado para el mismo paciente, profesional y fecha
        if (turnoRepository.existsByPacienteAndProfesionalAndFecha(paciente, profesional, turno.getFecha())) {
            throw new DatoInvalidoException("Ya existe un turno para ese paciente, profesional y fecha");
        }

        turno.setPaciente(paciente);
        turno.setProfesional(profesional);
        return turnoRepository.save(turno);
    }

    @Override
    public Turno obtenerTurnoPorId(Long id) {
        // Lanza excepción si el turno no existe en el sistema
        return turnoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Turno no encontrado con id: " + id));
    }

    @Override
    public List<Turno> obtenerTurnos() {
        return turnoRepository.findAll();
    }

    @Override
    public List<Turno> obtenerTurnosPorFecha(LocalDate fecha) {
        return turnoRepository.findByFecha(fecha);
    }

    @Override
    public void eliminarTurno(Long id) {
        // Verifica que el turno exista antes de eliminar
        obtenerTurnoPorId(id);
        turnoRepository.deleteById(id);
    }
}
