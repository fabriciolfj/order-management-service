package com.github.fabriciolfj.order_management.domain.entities;

import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
@ToString
@Builder
public class Item {
    private String code;
    private int quantity;
    private BigDecimal value;
    private BigDecimal total;
}
