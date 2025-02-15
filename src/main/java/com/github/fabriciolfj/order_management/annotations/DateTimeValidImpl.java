package com.github.fabriciolfj.order_management.annotations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDateTime;
import java.util.Objects;

import static com.github.fabriciolfj.order_management.utils.DateFormatUtil.DATE_TIME_FORMAT;

public class DateTimeValidImpl implements ConstraintValidator<DateTimeValid, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (Objects.isNull(value)) {
            return false;
        }

        try {
            LocalDateTime.parse(value, DATE_TIME_FORMAT);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
