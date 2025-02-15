package com.github.fabriciolfj.order_management.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fabriciolfj.order_management.OrderManagementApplicationTests;
import com.github.fabriciolfj.order_management.adapters.data.jpa.ItemJpa;
import com.github.fabriciolfj.order_management.adapters.data.jpa.OrderJpa;
import com.github.fabriciolfj.order_management.adapters.data.repositories.OrderRepository;
import com.github.fabriciolfj.order_management.entrypoint.controller.order.dto.DataOrderResponse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.testcontainers.containers.PostgreSQLContainer;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

import static com.github.fabriciolfj.order_management.utils.DateFormatUtil.DATE_TIME_FORMAT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class OrderControllerTest extends OrderManagementApplicationTests {

    private static PostgreSQLContainer<?> postgresContainer;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeAll
    void populateDatabase() {
        final List<OrderJpa> orders = IntStream.range(0, 1000)
                .mapToObj(i -> {
                    ItemJpa item = ItemJpa.builder()
                            .code(UUID.randomUUID().toString())
                            .quantity(1 + i % 10)
                            .value(new BigDecimal("100.00"))
                            .total(new BigDecimal("100.00").multiply(new BigDecimal(1 + i % 10)))
                            .build();

                    ItemJpa item2 = ItemJpa.builder()
                            .code(UUID.randomUUID().toString())
                            .quantity(1 + i % 10)
                            .value(new BigDecimal("100.00"))
                            .total(new BigDecimal("100.00").multiply(new BigDecimal(1 + i % 10)))
                            .build();

                    OrderJpa order = OrderJpa.builder()
                            .code(UUID.randomUUID().toString())
                            .tracking(UUID.randomUUID().toString())
                            .total(item.getTotal())
                            .dateReceive(LocalDateTime.now().minusDays(i % 365))
                            .createdAt(LocalDateTime.now())
                            .build();

                    order.setItems(List.of(item, item2));

                    return order;
                })
                .toList();

        for (int i = 0; i < orders.size(); i += 1000) {
            int end = Math.min(i + 1000, orders.size());
            orderRepository.saveAll(orders.subList(i, end));
        }
    }

    @Test
    void shouldGetOrdersWithPagination() throws Exception {
        final String dateInit = LocalDateTime.now().minusMinutes(30).format(DATE_TIME_FORMAT);
        final String dateEnd = LocalDateTime.now().plusMinutes(30).format(DATE_TIME_FORMAT);

        final MvcResult result = mockMvc.perform(get("/api/v1/orders")
                        .param("dateInit", dateInit)
                        .param("dateEnd", dateEnd)
                        .param("page", "0")
                        .param("total_elements", "20")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.orders").isArray())
                .andExpect(jsonPath("$.orders.length()").value(is(greaterThan(0))))
                .andExpect(jsonPath("$.totalElements").isNumber())
                .andExpect(jsonPath("$.totalPage").isNumber())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        var page = objectMapper.readValue(content, DataOrderResponse.class);

        assertThat(page.getOrders()).isNotEmpty();
        assertThat(page.getOrders().size()).isLessThanOrEqualTo(20);
    }

    @Test
    void shouldHandleInvalidDateFormat() throws Exception {
        mockMvc.perform(get("/api/v1/orders")
                        .param("dateInit", "invalid-date")
                        .param("dateEnd", LocalDateTime.now().toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}
