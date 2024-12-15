package com.example.telexpress.entity;

import com.example.telexpress.config.LuhnConstraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class LuhnValidacion  implements ConstraintValidator<LuhnConstraint, String> {
    @Override
    public void initialize(LuhnConstraint constraintAnnotation) {
        // Inicialización si se necesita
    }
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) {
            return false; // No es válido si está vacío
        }

        return isValidCardNumber(value);
    }
    public static boolean isValidCardNumber(String cardNumber) {
        if (cardNumber == null || cardNumber.isEmpty()) {
            return false;
        }
        cardNumber = cardNumber.replaceAll("\\s+", ""); // Eliminar espacios
        if (!cardNumber.matches("\\d+")) { // Asegura que contiene solo dígitos
            return false;
        }

        int total = 0;
        boolean alternate = false;

        // Itera desde el último dígito hacia el primero
        for (int i = cardNumber.length() - 1; i >= 0; i--) {
            char c = cardNumber.charAt(i);
            if (!Character.isDigit(c)) {
                return false; // Si contiene caracteres no numéricos
            }
            int n = Character.getNumericValue(c);

            if (alternate) {
                n *= 2;
                if (n > 9) {
                    n -= 9;
                }
            }

            total += n;
            alternate = !alternate;
        }

        return (total % 10 == 0);
    }
}
