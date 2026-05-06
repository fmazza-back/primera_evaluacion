package com.clinica.turnos.service;

import com.clinica.turnos.dto.TurnoRequestDTO;
import com.clinica.turnos.model.Turno;

import java.time.LocalDate;
import java.util.List;

// Contrato de servicio para la gestión de turnos médicos
public interface ITurnos {

    // Registra un nuevo turno validando paciente, profesional y duplicados
    Turno crearTurno(TurnoRequestDTO dto);

    // Retorna un turno por su identificador, lanza excepción si no existe
    Turno obtenerTurnoPorId(Long id);

    // Retorna la lista completa de turnos registrados
    List<Turno> obtenerTurnos();

    // Retorna los turnos correspondientes a una fecha específica
    List<Turno> obtenerTurnosPorFecha(LocalDate fecha);

    // Retorna los turnos dentro de un rango de fechas
    List<Turno> obtenerTurnosPorRangoDeFechas(LocalDate desde, LocalDate hasta);

    // Elimina un turno por su identificador
    void eliminarTurno(Long id);
}
