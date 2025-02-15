package com.github.fabriciolfj.order_management.entrypoint.listener.order;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemDto {

    @NotBlank(message = "value code invalid")
    private String code;
    @Positive(message = "quantity invalid")
    private int quantity;
    @Positive(message = "value invalid")
    private BigDecimal value;
}
