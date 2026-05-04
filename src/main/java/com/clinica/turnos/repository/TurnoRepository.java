package com.clinica.turnos.repository;

import com.clinica.turnos.model.Paciente;
import com.clinica.turnos.model.Profesional;
import com.clinica.turnos.model.Turno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TurnoRepository extends JpaRepository<Turno, Long> {

    List<Turno> findByFecha(LocalDate fecha);

    boolean existsByPacienteAndProfesionalAndFecha(Paciente paciente, Profesional profesional, LocalDate fecha);
}
