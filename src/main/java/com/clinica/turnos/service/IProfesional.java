package com.clinica.turnos.service;

import com.clinica.turnos.model.Profesional;

import java.util.List;

// Contrato de servicio para la gestión de profesionales médicos
public interface IProfesional {

    // Persiste un nuevo profesional en el sistema
    Profesional crearProfesional(Profesional profesional);

    // Retorna un profesional por su identificador, lanza excepción si no existe
    Profesional obtenerProfesionalPorId(Long id);

    // Retorna la lista completa de profesionales registrados
    List<Profesional> obtenerProfesionales();

    // Filtra profesionales según la especialidad indicada
    List<Profesional> obtenerProfesionalesPorEspecialidad(String especialidad);

    // Elimina un profesional por su identificador
    void eliminarProfesional(Long id);
}
