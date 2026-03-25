package com.autopro.autoproapi.validations.CPF;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CPFValidator implements ConstraintValidator<ValidCPF, String> {

    @Override
    public boolean isValid(String cpf, ConstraintValidatorContext context) {

        if (cpf == null || !cpf.matches("\\d{11}")) {
            return false;
        }

        // Elimina CPFs inválidos conhecidos
        if (cpf.chars().distinct().count() == 1) {
            return false;
        }

        try {
            // Primeiro dígito verificador
            int sum = 0;
            for (int i = 0; i < 9; i++) {
                sum += (cpf.charAt(i) - '0') * (10 - i);
            }

            int firstDigit = 11 - (sum % 11);
            if (firstDigit >= 10) firstDigit = 0;

            // Segundo dígito verificador
            sum = 0;
            for (int i = 0; i < 10; i++) {
                sum += (cpf.charAt(i) - '0') * (11 - i);
            }

            int secondDigit = 11 - (sum % 11);
            if (secondDigit >= 10) secondDigit = 0;

            return firstDigit == (cpf.charAt(9) - '0') &&
                    secondDigit == (cpf.charAt(10) - '0');

        } catch (Exception e) {
            return false;
        }
    }
}