package com.github.fabriciolfj.order_management.adapters.data.jpa;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "order")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderJpa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "code", nullable = false, unique = true)
    private String code;
    @Column(name = "tracking", nullable = false, unique = true)
    private String tracking;
    @ManyToOne(cascade = CascadeType.PERSIST)
    private List<ItemJpa> items = new ArrayList<>();
    @Column(name = "date_receive", nullable = false)
    private LocalDateTime dateReceive;
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    @Version
    private Long version;

}
