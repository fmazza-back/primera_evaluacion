package com.clinica.turnos.runner;

import com.clinica.turnos.dto.TurnoRequestDTO;
import com.clinica.turnos.model.Paciente;
import com.clinica.turnos.model.Profesional;
import com.clinica.turnos.model.Turno;
import com.clinica.turnos.service.IPaciente;
import com.clinica.turnos.service.IProfesional;
import com.clinica.turnos.service.ITurnos;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

// Runner que carga datos iniciales y prueba los principales filtros al iniciar la aplicación
@Component
public class CargaInicialRunner implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(CargaInicialRunner.class);

    // Spring inyecta automáticamente las implementaciones de cada interfaz de servicio
    @Autowired
    private IPaciente pacienteService;

    @Autowired
    private IProfesional profesionalService;

    @Autowired
    private ITurnos turnoService;

    @Override
    public void run(String... args) {
        log.info("=== Iniciando carga inicial de datos ===");

        // Crear 2 pacientes
        Paciente p1 = new Paciente();
        p1.setNombre("Juan");
        p1.setApellido("Pérez");
        p1.setDni("12345678");
        p1.setEmail("juan.perez@email.com");
        p1 = pacienteService.crearPaciente(p1);
        log.info("Paciente creado: {} {} (id={})", p1.getNombre(), p1.getApellido(), p1.getId());

        Paciente p2 = new Paciente();
        p2.setNombre("María");
        p2.setApellido("González");
        p2.setDni("87654321");
        p2.setEmail("maria.gonzalez@email.com");
        p2 = pacienteService.crearPaciente(p2);
        log.info("Paciente creado: {} {} (id={})", p2.getNombre(), p2.getApellido(), p2.getId());

        // Crear 2 profesionales: uno de clínica y uno de odontología
        Profesional prof1 = new Profesional();
        prof1.setNombreCompleto("Dr. Carlos Médico");
        prof1.setEspecialidad("Clínica");
        prof1 = profesionalService.crearProfesional(prof1);
        log.info("Profesional creado: {} - {} (id={})", prof1.getNombreCompleto(), prof1.getEspecialidad(), prof1.getId());

        Profesional prof2 = new Profesional();
        prof2.setNombreCompleto("Dra. Ana Dental");
        prof2.setEspecialidad("Odontología");
        prof2 = profesionalService.crearProfesional(prof2);
        log.info("Profesional creado: {} - {} (id={})", prof2.getNombreCompleto(), prof2.getEspecialidad(), prof2.getId());

        // Registrar 3 turnos
        TurnoRequestDTO dto1 = new TurnoRequestDTO();
        dto1.setPacienteId(p1.getId());
        dto1.setProfesionalId(prof1.getId());
        dto1.setFecha(LocalDate.now());
        Turno t1 = turnoService.crearTurno(dto1);
        log.info("Turno registrado: id={} | Paciente: {} | Profesional: {} | Fecha: {}", t1.getId(), p1.getApellido(), prof1.getNombreCompleto(), t1.getFecha());

        TurnoRequestDTO dto2 = new TurnoRequestDTO();
        dto2.setPacienteId(p2.getId());
        dto2.setProfesionalId(prof2.getId());
        dto2.setFecha(LocalDate.now());
        Turno t2 = turnoService.crearTurno(dto2);
        log.info("Turno registrado: id={} | Paciente: {} | Profesional: {} | Fecha: {}", t2.getId(), p2.getApellido(), prof2.getNombreCompleto(), t2.getFecha());

        TurnoRequestDTO dto3 = new TurnoRequestDTO();
        dto3.setPacienteId(p1.getId());
        dto3.setProfesionalId(prof2.getId());
        dto3.setFecha(LocalDate.now().plusDays(3));
        Turno t3 = turnoService.crearTurno(dto3);
        log.info("Turno registrado: id={} | Paciente: {} | Profesional: {} | Fecha: {}", t3.getId(), p1.getApellido(), prof2.getNombreCompleto(), t3.getFecha());

        // Prueba de filtros
        log.info("--- Filtro: turnos del día de hoy ---");
        List<Turno> turnosHoy = turnoService.obtenerTurnosPorFecha(LocalDate.now());
        turnosHoy.forEach(t -> log.info("  Turno id={} | {} | {}", t.getId(), t.getPaciente().getApellido(), t.getFecha()));

        log.info("--- Filtro: rango de fechas (hoy hasta +5 días) ---");
        List<Turno> turnosRango = turnoService.obtenerTurnosPorRangoDeFechas(LocalDate.now(), LocalDate.now().plusDays(5));
        turnosRango.forEach(t -> log.info("  Turno id={} | {} | {}", t.getId(), t.getPaciente().getApellido(), t.getFecha()));

        log.info("--- Filtro: profesionales con especialidad Odontología ---");
        List<Profesional> odontologos = profesionalService.obtenerProfesionalesPorEspecialidad("Odontología");
        odontologos.forEach(p -> log.info("  Profesional: {}", p.getNombreCompleto()));

        log.info("=== Carga inicial finalizada ===");
    }
}
