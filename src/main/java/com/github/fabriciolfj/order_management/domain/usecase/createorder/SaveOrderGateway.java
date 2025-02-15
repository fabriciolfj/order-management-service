package com.github.fabriciolfj.order_management.domain.usecase.createorder;

import com.github.fabriciolfj.order_management.domain.entities.Order;

import java.util.List;

public interface SaveOrderGateway {

    void process(final List<Order> orders);
}
