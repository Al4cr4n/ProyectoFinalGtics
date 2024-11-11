package com.example.telexpress.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PersonaDTO {
    @JsonProperty("nombres") //para indicar con que nobre est[a colocado realmente en la api
    private String nombres; //ambos nombres
    private String apellidoPaterno; //apellido paterno
    private String apellidoMaterno; //apellido materno
    private String tipoDocumento; //dni o ruc
    private String numeroDocumento; //el numero de dni o ruc
}
