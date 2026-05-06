package com.clinica.turnos.service;

import com.clinica.turnos.exception.RecursoNoEncontradoException;
import com.clinica.turnos.model.Profesional;
import com.clinica.turnos.repository.ProfesionalRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

// Implementación de la lógica de negocio para la gestión de profesionales
@Service
public class ProfesionalServiceImpl implements IProfesional {

    private static final Logger log = LoggerFactory.getLogger(ProfesionalServiceImpl.class);

    private final ProfesionalRepository repository;

    public ProfesionalServiceImpl(ProfesionalRepository repository) {
        this.repository = repository;
    }

    @Override
    public Profesional crearProfesional(Profesional profesional) {
        log.info("Creando profesional: {}", profesional.getNombreCompleto());
        Profesional guardado = repository.save(profesional);
        log.info("Profesional creado con id={}", guardado.getId());
        return guardado;
    }

    @Override
    public Profesional obtenerProfesionalPorId(Long id) {
        log.info("Buscando profesional con id={}", id);
        // Lanza excepción si el profesional no existe en el sistema
        return repository.findById(id)
                .orElseThrow(() -> {
                    log.error("Profesional no encontrado con id: {}", id);
                    return new RecursoNoEncontradoException("Profesional no encontrado con id: " + id);
                });
    }

    @Override
    public List<Profesional> obtenerProfesionales() {
        log.info("Listando todos los profesionales");
        return repository.findAll();
    }

    @Override
    public List<Profesional> obtenerProfesionalesPorEspecialidad(String especialidad) {
        log.info("Buscando profesionales con especialidad={}", especialidad);
        return repository.findByEspecialidad(especialidad);
    }

    @Override
    public void eliminarProfesional(Long id) {
        log.info("Eliminando profesional con id={}", id);
        // Verifica que el profesional exista antes de eliminar
        obtenerProfesionalPorId(id);
        repository.deleteById(id);
        log.info("Profesional eliminado con id={}", id);
    }
}
