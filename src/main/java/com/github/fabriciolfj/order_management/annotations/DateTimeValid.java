package com.github.fabriciolfj.order_management.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = DateTimeValidImpl.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface DateTimeValid {
    String message() default "Value invalid";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
