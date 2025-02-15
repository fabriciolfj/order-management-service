package com.github.fabriciolfj.order_management.adapters.data.jpa;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "item")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItemJpa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "code", nullable = false, unique = true)
    private String code;
    @Column(name = "quantity", nullable = false)
    private int quantity;
    @Column(name = "value", scale = 4, nullable = false)
    private BigDecimal value;
    @Column(name = "total", scale = 4, nullable = false)
    private BigDecimal total;
    @OneToMany(mappedBy = "items")
    private OrderJpa order;
}
