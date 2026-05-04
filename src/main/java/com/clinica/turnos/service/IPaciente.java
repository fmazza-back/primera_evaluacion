package com.clinica.turnos.service;

import com.clinica.turnos.model.Paciente;

import java.util.List;

public interface IPaciente {

    Paciente crearPaciente(Paciente paciente);
    Paciente obtenerPacientePorId(Long id);
    List<Paciente> obtenerPacientes();
    Paciente actualizarPaciente(Long id, Paciente paciente);
    void eliminarPaciente(Long id);
}
