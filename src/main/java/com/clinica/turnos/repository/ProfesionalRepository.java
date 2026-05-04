package com.clinica.turnos.repository;

import com.clinica.turnos.model.Profesional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfesionalRepository extends JpaRepository<Profesional, Long> {

    List<Profesional> findByEspecialidad(String especialidad);
}
