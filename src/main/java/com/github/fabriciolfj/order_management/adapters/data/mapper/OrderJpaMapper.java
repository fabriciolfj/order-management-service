package com.github.fabriciolfj.order_management.adapters.data.mapper;

import com.github.fabriciolfj.order_management.adapters.data.jpa.ItemJpa;
import com.github.fabriciolfj.order_management.adapters.data.jpa.OrderJpa;
import com.github.fabriciolfj.order_management.domain.entities.Item;
import com.github.fabriciolfj.order_management.domain.entities.Order;

import java.util.List;
import java.util.stream.Collectors;

public class OrderJpaMapper {

    public static OrderJpa toJpa(final Order order) {
        return OrderJpa.builder()
                .code(order.getCode())
                .dateReceive(order.getDateReceive())
                .tracking(order.getTracking())
                .items(toItemJpa(order.getItems()))
                .total(order.getTotal())
                .build();
    }

    private static List<ItemJpa> toItemJpa(final List<Item> items) {
        return items.stream().map(i ->
                ItemJpa.builder()
                        .code(i.getCode())
                        .value(i.getValue())
                        .quantity(i.getQuantity())
                        .total(i.getTotal())
                        .build())
                .collect(Collectors.toList());
    }
}
