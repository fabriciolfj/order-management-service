package com.github.fabriciolfj.order_management.entrypoint.controller.order.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseDto {

    private String code;
    private String tracking;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String dateReceive;
    private List<ItemResponseDto> items;
}
