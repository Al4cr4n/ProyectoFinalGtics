package com.example.telexpress.service;

import com.example.telexpress.dto.PersonaResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class UsuarioService {

    private static final Logger logger = LoggerFactory.getLogger(UsuarioService.class);
    private static final String RENIEC_API_URL = "https://api.apis.net.pe/v2/reniec/dni?numero={dni}";

    // Este es un ejemplo, consulta la documentación para el encabezado correcto.
    // Supongamos que el token se envía en el encabezado 'Authorization' con el formato 'Bearer <token>'
    private static final String API_TOKEN = "apis-token-11385.NY0mPBbdLdYOVIDVR8zBj1YGTRAQuwfu"; // Reemplaza con tu token real

    @Autowired
    private RestTemplate restTemplate;

    public PersonaResponseDTO obtenerDatosPorDni(String dni) {
        logger.info("Consultando datos en RENIEC para DNI: {}", dni);
        try {
            // Crear encabezados y agregar el token
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + API_TOKEN);

            HttpEntity<String> entity = new HttpEntity<>(headers);

            // Realizar la solicitud con exchange para poder pasar headers
            ResponseEntity<PersonaResponseDTO> response = restTemplate.exchange(
                    RENIEC_API_URL,
                    HttpMethod.GET,
                    entity,
                    PersonaResponseDTO.class,
                    dni
            );

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                PersonaResponseDTO personaDTO = response.getBody();

                // Concatenar apellidos
                String apellidoCompleto = "";
                if (personaDTO.getApellidoPaterno() != null) {
                    apellidoCompleto += personaDTO.getApellidoPaterno();
                }
                if (personaDTO.getApellidoMaterno() != null) {
                    apellidoCompleto += " " + personaDTO.getApellidoMaterno();
                }
                personaDTO.setApellidoCompleto(apellidoCompleto.trim());

                logger.debug("Datos obtenidos de RENIEC: {}", personaDTO);
                return personaDTO;
            } else {
                logger.warn("Respuesta no exitosa de RENIEC para DNI: {}", dni);
                return null;
            }
        } catch (HttpClientErrorException e) {
            logger.error("Error al consumir la API de RENIEC: {}", e.getMessage());
            return null;
        } catch (ResourceAccessException e) {
            logger.error("Timeout o error de conexión al consumir la API de RENIEC: {}", e.getMessage());
            return null;
        } catch (Exception e) {
            logger.error("Excepción inesperada al consumir la API de RENIEC: {}", e.getMessage());
            return null;
        }
    }

    // Otros métodos existentes en tu servicio
}
