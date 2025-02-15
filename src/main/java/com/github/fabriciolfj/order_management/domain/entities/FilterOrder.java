package com.github.fabriciolfj.order_management.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class FilterOrder {

    private int page;
    private int totalPerPage;
    private LocalDateTime dateInit;
    private LocalDateTime dateEnd;
}
