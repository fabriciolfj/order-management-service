package com.github.fabriciolfj.order_management.adapters.order;

import com.github.fabriciolfj.order_management.adapters.data.mapper.OrderJpaMapper;
import com.github.fabriciolfj.order_management.adapters.data.repositories.OrderRepository;
import com.github.fabriciolfj.order_management.domain.entities.Order;
import com.github.fabriciolfj.order_management.domain.usecase.createorder.SaveOrderGateway;
import jakarta.persistence.OptimisticLockException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class SaveOrderAdapter implements SaveOrderGateway {

    private final OrderRepository orderRepository;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public void process(final List<Order> orders) {
        try {
            log.info("init save orders {}", orders.size());
            var orderJpa = orders
                    .stream().map(OrderJpaMapper::toJpa).toList();

            orderRepository.saveAll(orderJpa);
        } catch (ConstraintViolationException | DataIntegrityViolationException | OptimisticLockException e) {
            log.error("fail save order duplicate or outdated, details {}", e.getMessage());
        }
    }
}
