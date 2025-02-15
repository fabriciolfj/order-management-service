package com.github.fabriciolfj.order_management.exceptions.errors;

import com.github.fabriciolfj.order_management.exceptions.infrastructure.DateIntervalInvalidException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(DateIntervalInvalidException.class)
    public ResponseEntity<?> handlerIntervalInvalidException(final DateIntervalInvalidException e) {
        var error = ErrorDto.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .message(e.getMessage())
                .build();

        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handlerException(final Exception e) {
        var error = ErrorDto.builder()
                .code(HttpStatus.SERVICE_UNAVAILABLE.value())
                .message(e.getMessage())
                .build();

        return ResponseEntity.internalServerError().body(error);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> handleConstraintViolationException(final ConstraintViolationException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ErrorDto.builder()
                        .code(HttpStatus.BAD_REQUEST.value())
                        .message("validação dos campos da requisição")
                        .details(mappingError(e))
                        .build());
    }

    private List<ErrorDtoDetails> mappingError(final ConstraintViolationException e) {
        return e.getConstraintViolations()
                .stream().map(obj -> {
                    final String message = obj.getMessage();
                    final String name;

                    if (obj instanceof FieldError) {
                        name = ((FieldError) obj).getField();
                    } else {
                        name = obj.getPropertyPath().toString();
                    }

                    return ErrorDtoDetails
                            .builder()
                            .field(name)
                            .message(message)
                            .build();
                }).collect(Collectors.toList());
    }
}
