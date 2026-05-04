package com.clinica.turnos.service;

import com.clinica.turnos.model.Paciente;

import java.util.List;

// Contrato de servicio para la gestión de pacientes
public interface IPaciente {

    // Persiste un nuevo paciente en el sistema
    Paciente crearPaciente(Paciente paciente);

    // Retorna un paciente por su identificador, lanza excepción si no existe
    Paciente obtenerPacientePorId(Long id);

    // Retorna la lista completa de pacientes registrados
    List<Paciente> obtenerPacientes();

    // Actualiza los datos de un paciente existente
    Paciente actualizarPaciente(Long id, Paciente paciente);

    // Elimina un paciente por su identificador
    void eliminarPaciente(Long id);
}
