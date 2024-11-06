package com.example.telexpress.dao;
import com.example.telexpress.dto.PersonaDTO;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;
@Component
public class ApisDni {
    @Value("${apis.token}")
    private String token;
    private static final String URL = "https://api.apis.net.pe/v2/reniec/dni?numero=";
    public PersonaDTO obtenerdatospordni(String dni){
        RestTemplate restTemplate = new RestTemplate();
        //se establecen los encabezados del token
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer" + token);
        //crea la entidad con los encabezados
        HttpEntity<String> entity = new HttpEntity<>(headers);
        //creando la entidad con los encabezados
        ResponseEntity<PersonaDTO> response = restTemplate.exchange(
                URL + dni,
                HttpMethod.GET,
                entity,
                PersonaDTO.class
        );
        return response.getBody();
    }

}
