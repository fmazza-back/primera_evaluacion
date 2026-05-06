package com.clinica.turnos.exception;

// Excepción lanzada cuando no se encuentra un recurso solicitado (HTTP 404)
public class RecursoNoEncontradoException extends RuntimeException {

    public RecursoNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}
