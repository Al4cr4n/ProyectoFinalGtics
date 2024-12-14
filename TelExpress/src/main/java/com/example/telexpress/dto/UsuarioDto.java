package com.example.telexpress.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UsuarioDto {
    private String nombre;
    private String apellido;
    private String correo;
    private String telefono;
    private String dni;
    private String estadoUsuario;
    private String motivo;
    private String ruc;
    private String razonSocial;
    private LocalDate fechanacimiento;
    // Agregar más propiedades si es necesario
}
