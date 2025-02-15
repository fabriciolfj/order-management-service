package com.github.fabriciolfj.order_management.entrypoint.controller.order.mapper;

import com.github.fabriciolfj.order_management.domain.entities.FilterOrder;
import com.github.fabriciolfj.order_management.exceptions.infrastructure.DateIntervalInvalidException;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.github.fabriciolfj.order_management.constants.RequestGetOrderConst.HOURS_DEFAULT_DATE_INIT;
import static com.github.fabriciolfj.order_management.utils.DateFormatUtil.DATE_TIME_FORMAT;

public class FilterOrderMapper {

    public static FilterOrder toFilter(
            final int page,
            final int totalElements,
            final String dateInit,
            final String dateEnd) {

        var dateStart = LocalDateTime.parse(dateInit, DATE_TIME_FORMAT);
        var dataEndValid = LocalDateTime.parse(dateEnd, DATE_TIME_FORMAT);

        if (dateStart.isAfter(dataEndValid)) {
            throw new DateIntervalInvalidException();
        }

        return FilterOrder.builder()
                .dateEnd(dataEndValid)
                .dateInit(dateStart)
                .totalPerPage(totalElements)
                .page(page)
                .build();
    }
}
