package com.example.telexpress.config;

import com.example.telexpress.entity.LuhnValidacion;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = LuhnValidacion.class)
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface  LuhnConstraint {
    String message() default "El número de tarjeta no es válido de acuerdo a Luhn";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
