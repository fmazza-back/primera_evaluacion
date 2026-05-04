package com.clinica.turnos.repository;

import com.clinica.turnos.model.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Repositorio JPA para la entidad Paciente
// Hereda operaciones CRUD básicas de JpaRepository
@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long> {
}
