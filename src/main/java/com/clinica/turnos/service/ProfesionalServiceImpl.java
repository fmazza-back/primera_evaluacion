package com.clinica.turnos.service;

import com.clinica.turnos.exception.RecursoNoEncontradoException;
import com.clinica.turnos.model.Profesional;
import com.clinica.turnos.repository.ProfesionalRepository;
import org.springframework.stereotype.Service;

import java.util.List;

// Implementación de la lógica de negocio para la gestión de profesionales
@Service
public class ProfesionalServiceImpl implements IProfesional {

    private final ProfesionalRepository repository;

    public ProfesionalServiceImpl(ProfesionalRepository repository) {
        this.repository = repository;
    }

    @Override
    public Profesional crearProfesional(Profesional profesional) {
        return repository.save(profesional);
    }

    @Override
    public Profesional obtenerProfesionalPorId(Long id) {
        // Lanza excepción si el profesional no existe en el sistema
        return repository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Profesional no encontrado con id: " + id));
    }

    @Override
    public List<Profesional> obtenerProfesionales() {
        return repository.findAll();
    }

    @Override
    public List<Profesional> obtenerProfesionalesPorEspecialidad(String especialidad) {
        return repository.findByEspecialidad(especialidad);
    }

    @Override
    public void eliminarProfesional(Long id) {
        // Verifica que el profesional exista antes de eliminar
        obtenerProfesionalPorId(id);
        repository.deleteById(id);
    }
}
