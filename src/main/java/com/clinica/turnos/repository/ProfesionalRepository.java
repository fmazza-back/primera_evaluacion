package com.clinica.turnos.repository;

import com.clinica.turnos.model.Profesional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

// Repositorio JPA para la entidad Profesional
// Hereda operaciones CRUD básicas de JpaRepository
@Repository
public interface ProfesionalRepository extends JpaRepository<Profesional, Long> {

    // Busca todos los profesionales que coincidan con la especialidad indicada
    List<Profesional> findByEspecialidad(String especialidad);
}
