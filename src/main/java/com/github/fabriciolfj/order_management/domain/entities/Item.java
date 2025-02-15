package com.github.fabriciolfj.order_management.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Item {
    private String code;
    private int quantity;
    private BigDecimal value;
    private BigDecimal total;
}
