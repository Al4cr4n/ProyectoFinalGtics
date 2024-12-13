package com.example.telexpress.entity;

public class LuhnValidacion {
    public static boolean isValidCardNumber(String cardNumber) {
        if (cardNumber == null || cardNumber.isEmpty()) {
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
