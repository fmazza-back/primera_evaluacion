package com.clinica.turnos.exception;

import com.clinica.turnos.dto.RespuestaDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

// Manejador global de excepciones: intercepta errores y devuelve respuestas HTTP coherentes
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // Maneja recursos no encontrados → HTTP 404
    @ExceptionHandler(RecursoNoEncontradoException.class)
    public ResponseEntity<RespuestaDTO<Void>> handleNotFound(RecursoNoEncontradoException ex) {
        log.error("Recurso no encontrado: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new RespuestaDTO<>(404, ex.getMessage(), null));
    }

    // Maneja datos inválidos o faltantes → HTTP 400
    @ExceptionHandler(DatoInvalidoException.class)
    public ResponseEntity<RespuestaDTO<Void>> handleInvalid(DatoInvalidoException ex) {
        log.warn("Dato inválido: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new RespuestaDTO<>(400, ex.getMessage(), null));
    }

    // Maneja errores de validación de @Valid → HTTP 400 con detalle por campo
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<RespuestaDTO<Map<String, String>>> handleValidacion(MethodArgumentNotValidException ex) {
        Map<String, String> errores = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errores.put(error.getField(), error.getDefaultMessage());
        }
        log.warn("Error de validación: {}", errores);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new RespuestaDTO<>(400, "Error de validación", errores));
    }

    // Maneja cualquier otro error no previsto → HTTP 500
    @ExceptionHandler(Exception.class)
    public ResponseEntity<RespuestaDTO<Void>> handleGeneral(Exception ex) {
        log.error("Error interno del servidor: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new RespuestaDTO<>(500, "Error interno del servidor", null));
    }
}
