package com.clinica.turnos.service;

import com.clinica.turnos.model.Profesional;

import java.util.List;

public interface IProfesional {

    Profesional crearProfesional(Profesional profesional);
    Profesional obtenerProfesionalPorId(Long id);
    List<Profesional> obtenerProfesionales();
    List<Profesional> obtenerProfesionalesPorEspecialidad(String especialidad);
    void eliminarProfesional(Long id);
}
