package com.github.fabriciolfj.order_management.entrypoint.controller.order.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DataOrderResponse {

    private long totalPage;
    private long totalElements;
    private List<OrderResponseDto> orders;
}
