package com.github.fabriciolfj.order_management.adapters.data.mapper;

import com.github.fabriciolfj.order_management.adapters.data.jpa.ItemJpa;
import com.github.fabriciolfj.order_management.adapters.data.jpa.OrderJpa;
import com.github.fabriciolfj.order_management.domain.entities.Item;
import com.github.fabriciolfj.order_management.domain.entities.ListOrder;
import com.github.fabriciolfj.order_management.domain.entities.Order;
import org.springframework.data.domain.Page;
import java.util.List;
import java.util.stream.Collectors;

public class OrderJpaMapper {

    public static ListOrder toListOrder(final Page<OrderJpa> pages) {
        return new ListOrder(
                pages.getTotalPages(),
                pages.getTotalElements(),
                toOrders(pages.getContent()));
    }

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

    private static List<Order> toOrders(final List<OrderJpa> ordersJpa) {
        return ordersJpa.stream().map(OrderJpaMapper::toOrder).toList();
    }

    private static Order toOrder(final OrderJpa orderJpa) {
        return Order.builder()
                .code(orderJpa.getCode())
                .tracking(orderJpa.getTracking())
                .dateReceive(orderJpa.getDateReceive())
                .items(toItems(orderJpa.getItems()))
                .total(orderJpa.getTotal())
                .build();
    }

    private static List<Item> toItems(final List<ItemJpa> itemsJpa) {
        return itemsJpa.stream().map(OrderJpaMapper::toItem)
                .toList();
    }

    private static Item toItem(final ItemJpa itemJpa) {
        return Item.builder()
                .quantity(itemJpa.getQuantity())
                .value(itemJpa.getValue())
                .code(itemJpa.getCode())
                .total(itemJpa.getTotal())
                .build();
    }
}
