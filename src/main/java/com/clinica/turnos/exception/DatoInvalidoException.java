package com.clinica.turnos.exception;

// Excepción lanzada cuando se recibe un dato inválido o incompleto (HTTP 400)
public class DatoInvalidoException extends RuntimeException {

    public DatoInvalidoException(String mensaje) {
        super(mensaje);
    }
}
