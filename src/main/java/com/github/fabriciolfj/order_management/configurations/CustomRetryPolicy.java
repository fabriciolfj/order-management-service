package com.github.fabriciolfj.order_management.configurations;

import jakarta.persistence.OptimisticLockException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.retry.RetryContext;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class CustomRetryPolicy extends SimpleRetryPolicy {

    private final Set<Class<? extends Throwable>> notRetryableExceptions = Set.of(
            DataIntegrityViolationException.class,
            OptimisticLockException.class,
            jakarta.validation.ConstraintViolationException.class,
            ConstraintViolationException.class);

    @Override
    public boolean canRetry(final RetryContext context) {
        final Throwable t = context.getLastThrowable();
        if (t != null && notRetryableExceptions.contains(t.getClass())) {
            return false;
        }
        return super.canRetry(context);
    }
}