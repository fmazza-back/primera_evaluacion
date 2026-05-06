package com.clinica.turnos.repository;

import com.clinica.turnos.model.Paciente;
import com.clinica.turnos.model.Profesional;
import com.clinica.turnos.model.Turno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

// Repositorio JPA para la entidad Turno
// Hereda operaciones CRUD básicas de JpaRepository
@Repository
public interface TurnoRepository extends JpaRepository<Turno, Long> {

    // Retorna todos los turnos registrados para una fecha determinada
    List<Turno> findByFecha(LocalDate fecha);

    // Retorna los turnos cuya fecha se encuentre dentro del rango indicado
    List<Turno> findByFechaBetween(LocalDate desde, LocalDate hasta);

    // Verifica si ya existe un turno con el mismo paciente, profesional y fecha (control de duplicados)
    boolean existsByPacienteAndProfesionalAndFecha(Paciente paciente, Profesional profesional, LocalDate fecha);
}
