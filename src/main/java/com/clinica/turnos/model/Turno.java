package com.clinica.turnos.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

// Entidad que representa un turno médico entre un paciente y un profesional
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Turno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relación muchos-a-uno: varios turnos pueden pertenecer a un mismo paciente
    @ManyToOne
    @JoinColumn(name = "paciente_id")
    private Paciente paciente;

    // Relación muchos-a-uno: varios turnos pueden asignarse al mismo profesional
    @ManyToOne
    @JoinColumn(name = "profesional_id")
    private Profesional profesional;

    private LocalDate fecha;
}
