package com.autopro.autoproapi.validations.Plate;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PlateValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPlate {

    String message() default "Placa inválida";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}