package com.github.fabriciolfj.order_management.constraints;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@Slf4j
@RequiredArgsConstructor
public class ValidatorConstraint {

    private final Validator validator;

    public <T> void validate(T object) {
        final Set<ConstraintViolation<T>> violations = validator.validate(object);

        if (!violations.isEmpty()) {
            log.error("validation failed for object type {}", object.getClass().getSimpleName());

            throw new ConstraintViolationException(
                    violations.stream()
                            .map(violation -> String.format("%s: %s",
                                    violation.getPropertyPath(),
                                    violation.getMessage()))
                            .collect(Collectors.joining(", ")),
                    violations
            );
        }
    }
}
