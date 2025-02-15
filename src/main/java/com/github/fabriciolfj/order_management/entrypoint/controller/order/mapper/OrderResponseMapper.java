package com.github.fabriciolfj.order_management.entrypoint.controller.order.mapper;

import com.github.fabriciolfj.order_management.domain.entities.Item;
import com.github.fabriciolfj.order_management.domain.entities.ListOrder;
import com.github.fabriciolfj.order_management.domain.entities.Order;
import com.github.fabriciolfj.order_management.entrypoint.controller.order.dto.DataOrderResponse;
import com.github.fabriciolfj.order_management.entrypoint.controller.order.dto.ItemResponseDto;
import com.github.fabriciolfj.order_management.entrypoint.controller.order.dto.OrderResponseDto;

import java.util.List;

public class OrderResponseMapper {

    public static DataOrderResponse toDataOrderResponse(final ListOrder listOrder) {
        return DataOrderResponse
                .builder()
                .totalPage(listOrder.getTotalPages())
                .totalElements(listOrder.getTotalPages())
                .orders(toOrdersResponseDto(listOrder.getOrders()))
                .build();
    }

    private static List<OrderResponseDto> toOrdersResponseDto(final List<Order> orders) {
        return orders.stream()
                .map(OrderResponseMapper::toOrderResponseDto)
                .toList();
    }

    private static OrderResponseDto toOrderResponseDto(final Order order) {
        return OrderResponseDto.builder()
                .code(order.getCode())
                .tracking(order.getTracking())
                .dateReceive(order.getDateReceive().toString())
                .items(toItemsResponseDto(order.getItems()))
                .build();
    }

    private static List<ItemResponseDto> toItemsResponseDto(final List<Item> items) {
        return items.stream()
                .map(OrderResponseMapper::toItemResponseDto)
                .toList();
    }

    private static ItemResponseDto toItemResponseDto(final Item item) {
        return ItemResponseDto.builder()
                .code(item.getCode())
                .value(item.getValue())
                .quantity(item.getQuantity())
                .build();
    }

}
