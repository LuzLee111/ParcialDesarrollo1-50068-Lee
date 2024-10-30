package org.example.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Constraint(validatedBy = DnaValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ValidDna {
    String message() default "Secuencia de ADN inválida";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}