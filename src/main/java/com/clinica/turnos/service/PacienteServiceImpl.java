package com.clinica.turnos.service;

import com.clinica.turnos.exception.RecursoNoEncontradoException;
import com.clinica.turnos.model.Paciente;
import com.clinica.turnos.repository.PacienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PacienteServiceImpl implements IPaciente {

    private final PacienteRepository repository;

    public PacienteServiceImpl(PacienteRepository repository) {
        this.repository = repository;
    }

    @Override
    public Paciente crearPaciente(Paciente paciente) {
        return repository.save(paciente);
    }

    @Override
    public Paciente obtenerPacientePorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Paciente no encontrado con id: " + id));
    }

    @Override
    public List<Paciente> obtenerPacientes() {
        return repository.findAll();
    }

    @Override
    public Paciente actualizarPaciente(Long id, Paciente datos) {
        Paciente existente = obtenerPacientePorId(id);
        existente.setNombre(datos.getNombre());
        existente.setApellido(datos.getApellido());
        existente.setDni(datos.getDni());
        existente.setEmail(datos.getEmail());
        return repository.save(existente);
    }

    @Override
    public void eliminarPaciente(Long id) {
        obtenerPacientePorId(id);
        repository.deleteById(id);
    }
}
