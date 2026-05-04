package com.clinica.turnos.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

// Manejador global de excepciones: intercepta errores y devuelve respuestas HTTP coherentes
@RestControllerAdvice
public class GlobalExceptionHandler {

    // Maneja recursos no encontrados → HTTP 404
    @ExceptionHandler(RecursoNoEncontradoException.class)
    public ResponseEntity<Map<String, String>> handleNotFound(RecursoNoEncontradoException ex) {
        Map<String, String> body = new HashMap<>();
        body.put("error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    // Maneja datos inválidos o faltantes → HTTP 400
    @ExceptionHandler(DatoInvalidoException.class)
    public ResponseEntity<Map<String, String>> handleInvalid(DatoInvalidoException ex) {
        Map<String, String> body = new HashMap<>();
        body.put("error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    // Maneja cualquier otro error no previsto → HTTP 500
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGeneral(Exception ex) {
        Map<String, String> body = new HashMap<>();
        body.put("error", "Error interno del servidor");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }
}
