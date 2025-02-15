package com.github.fabriciolfj.order_management.entrypoint.listener.order.dto;

import com.github.fabriciolfj.order_management.annotations.NotEmptyList;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderDto {

    @NotBlank(message = "code invalid")
    private String code;
    @NotBlank(message = "tracking invalid")
    private String tracking;
    @NotBlank(message = "date invalid")
    private LocalDateTime dateReceive;
    @NotEmptyList
    private List<ItemDto> items;
}
