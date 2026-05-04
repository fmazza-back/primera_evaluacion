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
        if (turno.getPaciente() == null || turno.getPaciente().getId() == null) {
            throw new DatoInvalidoException("El turno debe tener un paciente válido");
        }
        if (turno.getProfesional() == null || turno.getProfesional().getId() == null) {
            throw new DatoInvalidoException("El turno debe tener un profesional válido");
        }

        Paciente paciente = pacienteRepository.findById(turno.getPaciente().getId())
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "Paciente no encontrado con id: " + turno.getPaciente().getId()));

        Profesional profesional = profesionalRepository.findById(turno.getProfesional().getId())
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "Profesional no encontrado con id: " + turno.getProfesional().getId()));

        if (turnoRepository.existsByPacienteAndProfesionalAndFecha(paciente, profesional, turno.getFecha())) {
            throw new DatoInvalidoException("Ya existe un turno para ese paciente, profesional y fecha");
        }

        turno.setPaciente(paciente);
        turno.setProfesional(profesional);
        return turnoRepository.save(turno);
    }

    @Override
    public Turno obtenerTurnoPorId(Long id) {
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
        obtenerTurnoPorId(id);
        turnoRepository.deleteById(id);
    }
}
