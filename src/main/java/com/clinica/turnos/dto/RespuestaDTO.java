package com.clinica.turnos.dto;

// DTO genérico para unificar la estructura de todas las respuestas de la API
public class RespuestaDTO<T> {

    // Código HTTP de la respuesta
    private int estado;

    // Mensaje descriptivo del resultado
    private String mensaje;

    // Datos devueltos por la operación
    private T datos;

    public RespuestaDTO(int estado, String mensaje, T datos) {
        this.estado = estado;
        this.mensaje = mensaje;
        this.datos = datos;
    }

    public int getEstado() { return estado; }
    public String getMensaje() { return mensaje; }
    public T getDatos() { return datos; }
}
