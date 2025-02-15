package com.github.fabriciolfj.order_management.adapters.data.repositories;

import com.github.fabriciolfj.order_management.adapters.data.jpa.OrderJpa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface OrderRepository extends JpaRepository<OrderJpa, Long> {

    @Query(value = "SELECT DISTINCT o FROM OrderJpa o " +
            "JOIN FETCH o.items " +
            "WHERE COALESCE(:dateInit, o.dateReceive) <= o.dateReceive AND " +
            "COALESCE(:dateEnd, o.dateReceive) >= o.dateReceive",
            countQuery = "SELECT COUNT(DISTINCT o) FROM OrderJpa o " +
                    "WHERE COALESCE(:dateInit, o.dateReceive) <= o.dateReceive AND " +
                    "COALESCE(:dateEnd, o.dateReceive) >= o.dateReceive")
    Page<OrderJpa> getOrders(@Param("dateInit") LocalDateTime dateInit,
                             @Param("dateEnd") LocalDateTime dateEnd,
                             Pageable pageable);
}
