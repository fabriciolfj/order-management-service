package com.github.fabriciolfj.order_management.adapters.order;

import com.github.fabriciolfj.order_management.adapters.data.repositories.OrderRepository;
import com.github.fabriciolfj.order_management.domain.entities.FilterOrder;
import com.github.fabriciolfj.order_management.domain.entities.ListOrder;
import com.github.fabriciolfj.order_management.domain.usecase.findorder.GetOrderGateway;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.github.fabriciolfj.order_management.adapters.data.mapper.OrderJpaMapper.toListOrder;
import static org.springframework.data.domain.PageRequest.of;

@Slf4j
@RequiredArgsConstructor
@Component
public class GetOrderAdapter implements GetOrderGateway {

    private final OrderRepository orderRepository;

    @Override
    public ListOrder getOrders(final FilterOrder filterOrder) {
        log.info("request filter orders, interval {} {}", filterOrder.getDateInit(), filterOrder.getDateEnd());
        var page = orderRepository.getOrders(
                filterOrder.getDateInit(),
                filterOrder.getDateEnd(),
                of(filterOrder.getPage(), filterOrder.getTotalPerPage())
        );

        return toListOrder(page);
    }
}
