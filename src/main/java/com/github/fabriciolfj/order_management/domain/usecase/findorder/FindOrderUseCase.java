package com.github.fabriciolfj.order_management.domain.usecase.findorder;

import com.github.fabriciolfj.order_management.domain.entities.FilterOrder;
import com.github.fabriciolfj.order_management.domain.entities.ListOrder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class FindOrderUseCase {

    private final GetOrderGateway getOrderGateway;

    public ListOrder execute(final FilterOrder filterOrder) {
        var orders = getOrderGateway.getOrders(filterOrder);

        log.info("total order returned by filter {}, {}, {}", filterOrder.getDateInit(), filterOrder.getDateEnd(), orders.getTotalOrders());
        return orders;
    }
}
