package com.github.fabriciolfj.order_management.adapters.data.repositories;

import com.github.fabriciolfj.order_management.adapters.data.jpa.ItemJpa;
import com.github.fabriciolfj.order_management.adapters.data.jpa.OrderJpa;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class OrderRepositoryCustom {

    private final JdbcTemplate jdbcTemplate;

    public OrderRepositoryCustom(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional
    public void saveAllNative(List<OrderJpa> orders) {
        if (orders.isEmpty()) return;

        // 1. Batch insert das orders
        batchInsertOrders(orders);

        // 2. Batch insert dos items
        batchInsertItems(orders);
    }

    private void batchInsertOrders(List<OrderJpa> orders) {
        String sql = """
            INSERT INTO orders (code, tracking, total, date_receive, created_at, updated_at, version)
            VALUES (?, ?, ?, ?, NOW(), NOW(), 0)
            """;

        List<Object[]> batchArgs = orders.stream()
                .map(order -> new Object[]{
                        order.getCode(),
                        order.getTracking(),
                        order.getTotal(),
                        order.getDateReceive()
                })
                .toList();

        // Batch insert
        jdbcTemplate.batchUpdate(sql, batchArgs);

        // Recupera IDs gerados
        assignGeneratedIds(orders);
    }

    private void assignGeneratedIds(List<OrderJpa> orders) {
        // Cria lista de códigos para buscar IDs
        Object[] codes = orders.stream()
                .map(OrderJpa::getCode)
                .toArray();

        String placeholders = orders.stream()
                .map(o -> "?")
                .collect(Collectors.joining(","));

        String sql = "SELECT id, code FROM orders WHERE code IN (" + placeholders + ") ORDER BY code";

        Map<String, Long> codeToIdMap = jdbcTemplate.query(sql, codes, rs -> {
            Map<String, Long> map = new HashMap<>();
            while (rs.next()) {
                map.put(rs.getString("code"), rs.getLong("id"));
            }
            return map;
        });

        // Associa IDs às orders
        orders.forEach(order ->
                order.setId(codeToIdMap.get(order.getCode()))
        );
    }

    private void batchInsertItems(List<OrderJpa> orders) {
        // Coleta todos os items
        List<Object[]> itemsBatch = new ArrayList<>();

        for (OrderJpa order : orders) {
            for (ItemJpa item : order.getItems()) {
                itemsBatch.add(new Object[]{
                        item.getCode(),
                        item.getQuantity(),
                        item.getValue(),
                        item.getTotal(),
                        order.getId()
                });
            }
        }

        if (itemsBatch.isEmpty()) return;

        String sql = """
            INSERT INTO item (code, quantity, value, total, order_id)
            VALUES (?, ?, ?, ?, ?)
            """;

        jdbcTemplate.batchUpdate(sql, itemsBatch);
    }
}