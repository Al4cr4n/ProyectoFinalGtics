package com.example.telexpress.controller;

import com.example.telexpress.controller.errormanage.ResourceNotFoundException;
import com.example.telexpress.controller.errormanage.TooManyRequestsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    /*para documentacion, no se muestra como html*/
    // Manejo de excepciones específicas (por ejemplo, código despachador no encontrado)
    /*@ExceptionHandler(DespachadorNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleDespachadorNotFound(DespachadorNotFoundException ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("status", "error");
        errorResponse.put("mensaje", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }*/

    // Manejo de excepciones generales
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGeneralException(Exception ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("status", "error");
        errorResponse.put("mensaje", "Ocurrió un error inesperado");
        errorResponse.put("detalle", ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    // Manejo de error 401 (No autorizado)
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Map<String, String>> handleUnauthorized(ResponseStatusException ex) {
        if (HttpStatus.UNAUTHORIZED == ex.getStatusCode()) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("status", "error");
            errorResponse.put("mensaje", "No autorizado. Verifique sus credenciales.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        }
        throw ex;
    }

    // Manejo de error 403 (Prohibido)
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Map<String, String>> handleForbidden(AccessDeniedException ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("status", "error");
        errorResponse.put("mensaje", "Acceso prohibido. No tiene los permisos necesarios.");
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
    }

    // Manejo de error 404 (No encontrado)
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleNotFound(ResourceNotFoundException ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("status", "error");
        errorResponse.put("mensaje", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    // Manejo de error 429 (Demasiadas solicitudes)
    @ExceptionHandler(TooManyRequestsException.class)
    public ResponseEntity<Map<String, String>> handleTooManyRequests(TooManyRequestsException ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("status", "error");
        errorResponse.put("mensaje", "Demasiadas solicitudes. Por favor, espere antes de intentar nuevamente.");
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body(errorResponse);
    }
}

