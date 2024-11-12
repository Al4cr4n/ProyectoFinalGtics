package com.example.telexpress.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentResponseDTO {
    private Integer ordenId;
    private double monto;
    private String metodoPago; // Opcional: "tarjeta" o "paypal"
}
