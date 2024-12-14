package com.example.telexpress.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PersonaResponseDTO {
    private String nombres;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String tipoDocumento;
    private String numeroDocumento;
    private String apellidoCompleto; // Campo adicional para concatenar apellidos
}
