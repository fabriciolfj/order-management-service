package com.github.fabriciolfj.order_management.entrypoint.listener.order.mapper;

import com.github.fabriciolfj.order_management.domain.entities.Item;
import com.github.fabriciolfj.order_management.domain.entities.Order;
import com.github.fabriciolfj.order_management.entrypoint.listener.order.dto.ItemDto;
import com.github.fabriciolfj.order_management.entrypoint.listener.order.dto.OrderDto;

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
                                .items(toEntityItems(dto.getItems()))
                                .build().calcTotal())
                .toList();
    }

    private static List<Item> toEntityItems(final List<ItemDto> items)  {
        return items.stream()
                .map(item -> Item.builder()
                        .value(item.getPrice())
                        .total(item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                        .code(item.getCode())
                        .quantity(item.getQuantity())
                        .build())
                .toList();
    }
}
