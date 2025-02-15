package com.github.fabriciolfj.order_management.domain.entities;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class ListOrder {

    private long totalPages;
    private long total;
    private List<Order> orders;

    public long getTotalOrders() {
        return orders.size();
    }
}
