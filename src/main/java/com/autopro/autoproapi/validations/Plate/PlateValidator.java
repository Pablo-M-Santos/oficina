package com.autopro.autoproapi.validations.Plate;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PlateValidator implements ConstraintValidator<ValidPlate, String> {

    private static final String OLD_PATTERN = "^[A-Z]{3}\\d{4}$";
    private static final String MERCOSUL_PATTERN = "^[A-Z]{3}\\d[A-Z]\\d{2}$";

    @Override
    public boolean isValid(String plate, ConstraintValidatorContext context) {

        if (plate == null) {
            return false;
        }

        String normalized = normalize(plate);

        return normalized.matches(OLD_PATTERN) ||
                normalized.matches(MERCOSUL_PATTERN);
    }

    private String normalize(String plate) {
        return plate
                .replaceAll("[^A-Za-z0-9]", "")
                .toUpperCase();
    }
}