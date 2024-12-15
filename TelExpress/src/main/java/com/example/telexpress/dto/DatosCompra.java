package com.example.telexpress.dto;

import com.example.telexpress.config.LuhnConstraint;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class DatosCompra {
    @NotNull(message = "El ID de la orden es obligatorio")
    private Integer ordenId;

    @NotBlank(message = "El número de la tarjeta no puede estar vacío")
    @Size(min = 16, max = 16, message = "El numero de tarjeta debe tener 16 dígitos")
    @LuhnConstraint(message="El número de tarjeta no es válido o no existe")
    private String numeroTarjeta;

    @NotBlank(message = "El código CVV no puede estar vacío")
    @Size(min = 3, max = 3, message = "El código CVV debe tener 3 dígitos")
    private String codigoCvv;

    @NotNull(message = "La fecha de expiración no puede estar vacía")
    @Pattern(regexp = "^(0[1-9]|1[0-2])/(\\d{2})$", message = "La fecha debe tener el formato MM/YY")
    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/yy")
    private String fechaExpiracion;

    @NotNull(message = "El monto no puede ser nulo")
    @DecimalMin(value = "0.01", message = "El monto debe ser mayor a 0")
    private BigDecimal monto;

    @NotNull(message = "El correo es obligatorio")
    @Email(message = "El formato del correo es inválido")
    private String correo;
//correo temporal para el envio de boleta

    @NotNull(message = "La zona es obligatoria")
    private String zona;

    @NotNull(message = "La dirección es obligatoria")
    private String direccion;
    //direccion temporal para el envío de boleta

    @NotNull(message = "El nombre es obligatorio")
    @Size(min = 5, max = 50, message = "El nombre debe tener entre 5 y 50 caracteres")
    private String nombre;
}
