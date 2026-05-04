package com.clinica.turnos.service;

import com.clinica.turnos.model.Turno;

import java.time.LocalDate;
import java.util.List;

public interface ITurnos {

    Turno crearTurno(Turno turno);
    Turno obtenerTurnoPorId(Long id);
    List<Turno> obtenerTurnos();
    List<Turno> obtenerTurnosPorFecha(LocalDate fecha);
    void eliminarTurno(Long id);
}
