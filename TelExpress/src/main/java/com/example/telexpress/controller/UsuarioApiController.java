package com.example.telexpress.controller;

import com.example.telexpress.dto.PersonaResponseDTO;
import com.example.telexpress.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioApiController {

    private static final Logger logger = LoggerFactory.getLogger(UsuarioApiController.class);

    @Autowired
    private UsuarioService usuarioService;

    /**
     * Endpoint para obtener datos de usuario por DNI.
     * URL: GET /api/usuarios/dni/{dni}
     *
     * @param dni Número de DNI a consultar.
     * @return PersonaResponseDTO con los datos del usuario o mensajes de error apropiados.
     */
    @GetMapping("/dni/{dni}")
    public ResponseEntity<?> obtenerDatosPorDni(@PathVariable String dni) {
        logger.info("Recibida solicitud para DNI: {}", dni);

        String dniLimpio = dni.trim();
        logger.debug("DNI limpio: {}", dniLimpio);

        // Validar que el DNI tenga exactamente 8 dígitos
        if (!dniLimpio.matches("\\d{8}")) {
            logger.warn("DNI inválido: {}", dniLimpio);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El DNI debe contener exactamente 8 dígitos numéricos.");
        }

        PersonaResponseDTO personaDTO = usuarioService.obtenerDatosPorDni(dniLimpio);
        if (personaDTO != null) {
            logger.info("Datos encontrados para DNI: {}", dniLimpio);
            return new ResponseEntity<>(personaDTO, HttpStatus.OK);
        } else {
            logger.warn("No se encontraron datos para DNI: {}", dniLimpio);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontraron datos para el DNI proporcionado.");
        }
    }
}
