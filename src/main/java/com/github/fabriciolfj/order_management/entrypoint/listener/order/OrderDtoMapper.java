package com.github.fabriciolfj.order_management.entrypoint.listener.order;

import com.github.fabriciolfj.order_management.domain.entities.Item;
import com.github.fabriciolfj.order_management.domain.entities.Order;

import java.math.BigDecimal;
import java.util.List;

public class OrderDtoMapper {

    public static List<Order> toEntityOrders(final List<OrderDto> dtos) {
        return dtos
                .stream()
                .map(dto ->
                        Order.builder()
                                .dateReceive(dto.getDateReceive())
                                .code(dto.getCode())
                                .tracking(dto.getTracking())
                                .items(dto.getItems())
                                .build())
                .toList();
    }

    private static List<Item> toEntityItems(final List<Item> items)  {
        return items.stream()
                .map(item -> Item.builder()
                        .value(item.getValue())
                        .total(item.getTotal().multiply(BigDecimal.valueOf(item.getQuantity())))
                        .code(item.getCode())
                        .quantity(item.getQuantity())
                        .build())
                .toList();
    }
}
