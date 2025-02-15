package com.github.fabriciolfj.order_management.adapters.data.repositories;

import com.github.fabriciolfj.order_management.adapters.data.jpa.OrderJpa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderJpa, Long> {
}
