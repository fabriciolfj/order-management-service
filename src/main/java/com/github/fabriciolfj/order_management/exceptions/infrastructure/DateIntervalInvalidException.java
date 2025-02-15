package com.github.fabriciolfj.order_management.exceptions.infrastructure;

import static com.github.fabriciolfj.order_management.exceptions.errors.Errors.INTERVAL_INVALID;

public class DateIntervalInvalidException extends RuntimeException {

    public DateIntervalInvalidException() {
        super(INTERVAL_INVALID.toMessage());
    }
}
