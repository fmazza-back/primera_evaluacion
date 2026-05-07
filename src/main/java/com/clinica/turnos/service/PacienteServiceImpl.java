package com.clinica.turnos.service;

import com.clinica.turnos.exception.RecursoNoEncontradoException;
import com.clinica.turnos.model.Paciente;
import com.clinica.turnos.repository.PacienteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

// Implementación de la lógica de negocio para la gestión de pacientes
@Service
public class PacienteServiceImpl implements IPaciente {

    private static final Logger log = LoggerFactory.getLogger(PacienteServiceImpl.class);

    // Spring inyecta automáticamente la implementación de PacienteRepository al iniciar el contexto
    @Autowired
    private PacienteRepository repository;

    @Override
    public Paciente crearPaciente(Paciente paciente) {
        log.info("Creando paciente: {} {}", paciente.getNombre(), paciente.getApellido());
        Paciente guardado = repository.save(paciente);
        log.info("Paciente creado con id={}", guardado.getId());
        return guardado;
    }

    @Override
    public Paciente obtenerPacientePorId(Long id) {
        log.info("Buscando paciente con id={}", id);
        // Lanza excepción si el paciente no existe en el sistema
        return repository.findById(id)
                .orElseThrow(() -> {
                    log.error("Paciente no encontrado con id: {}", id);
                    return new RecursoNoEncontradoException("Paciente no encontrado con id: " + id);
                });
    }

    @Override
    public List<Paciente> obtenerPacientes() {
        log.info("Listando todos los pacientes");
        return repository.findAll();
    }

    @Override
    public Paciente actualizarPaciente(Long id, Paciente datos) {
        log.info("Actualizando paciente con id={}", id);
        // Verifica que el paciente exista antes de actualizar
        Paciente existente = obtenerPacientePorId(id);
        existente.setNombre(datos.getNombre());
        existente.setApellido(datos.getApellido());
        existente.setDni(datos.getDni());
        existente.setEmail(datos.getEmail());
        return repository.save(existente);
    }

    @Override
    public void eliminarPaciente(Long id) {
        log.info("Eliminando paciente con id={}", id);
        // Verifica que el paciente exista antes de eliminar
        obtenerPacientePorId(id);
        repository.deleteById(id);
        log.info("Paciente eliminado con id={}", id);
    }
}
