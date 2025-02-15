package com.github.fabriciolfj.order_management.entrypoint.controller.order.http;

import com.github.fabriciolfj.order_management.annotations.DateTimeValid;
import com.github.fabriciolfj.order_management.domain.usecase.findorder.FindOrderUseCase;
import com.github.fabriciolfj.order_management.entrypoint.controller.order.dto.DataOrderResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.github.fabriciolfj.order_management.entrypoint.controller.order.mapper.FilterOrderMapper.toFilter;
import static com.github.fabriciolfj.order_management.entrypoint.controller.order.mapper.OrderResponseMapper.toDataOrderResponse;

@Slf4j
@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
@Validated
public class OrderController {

    private final FindOrderUseCase findOrderUseCase;

    @GetMapping
    public DataOrderResponse getOrdersPageable(@RequestParam(value = "dateInit", required = false)
                                               @DateTimeValid final String dateInt,
                                               @RequestParam(value = "dateEndee", required = false)
                                               @DateTimeValid final String dateEnd,
                                               @RequestParam(value = "page", defaultValue = "0") int page,
                                               @RequestParam(value = "total_elements", defaultValue = "100") int totalElements) {

        final var filter = toFilter(page, totalElements, dateInt, dateEnd);
        final var orders = findOrderUseCase.execute(filter);
        log.info("total orders to response by filter {} {} {}", filter.getDateInit(), filter.getDateEnd(), orders.getTotalOrders());

        return toDataOrderResponse(orders);
    }
}
