package com.clinica.turnos.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// Entidad que representa a un profesional médico de la clínica
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Profesional {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombreCompleto;

    // Área médica en la que se desempeña el profesional
    private String especialidad;
}
