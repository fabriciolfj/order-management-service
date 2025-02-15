package com.github.fabriciolfj.order_management.domain.usecase.findorder;

import com.github.fabriciolfj.order_management.domain.entities.FilterOrder;
import com.github.fabriciolfj.order_management.domain.entities.ListOrder;

public interface GetOrderGateway {

    ListOrder getOrders(final FilterOrder filterOrder);
}
