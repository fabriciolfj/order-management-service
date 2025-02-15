package com.github.fabriciolfj.order_management.domain.entities;


import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Getter
@ToString
@Builder
public class Order {
    private String code;
    private String tracking;
    private LocalDateTime dateReceive;
    private List<Item> items;
    private BigDecimal total;

    public Order calcTotal() {
        this.total = items.stream()
                .map(Item::getTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return this;
    }
}
