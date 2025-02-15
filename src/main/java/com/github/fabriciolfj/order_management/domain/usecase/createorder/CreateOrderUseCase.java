package com.github.fabriciolfj.order_management.domain.usecase.createorder;

import com.github.fabriciolfj.order_management.domain.entities.Order;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class CreateOrderUseCase {

    private final SaveOrderGateway saveOrderGateway;

    public void execute(final List<Order> orders) {
        saveOrderGateway.process(orders);
        log.info("saved total orders {} success", orders.size());
    }
}
