package com.github.fabriciolfj.order_management.entrypoint.listener.order;

import com.github.fabriciolfj.order_management.domain.usecase.createorder.CreateOrderUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.github.fabriciolfj.order_management.entrypoint.listener.order.OrderDtoMapper.toEntityOrders;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderListener {

    private final CreateOrderUseCase createOrderUseCase;

    @RabbitListener(queues = "${queue.name}")
    public void receive(final List<OrderDto> dtos) {
        log.info("receive orders {}", dtos.size());

        var entities = toEntityOrders(dtos);
        createOrderUseCase.execute(entities);
    }
}
