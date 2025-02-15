package com.github.fabriciolfj.order_management.configurations;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderRabbitmqConfig {

    @Value("${queue.order.name}")
    private String orderQueue;

    @Value("${queue.order.dlq}")
    private String orderDlq;

    @Value("${queue.order.exchange}")
    private String orderExchange;

    @Value("${queue.order.routing-key}")
    private String routingKey;

    @Bean
    public Queue orderQueue() {
        return QueueBuilder.durable(orderQueue)
                .withArgument("x-dead-letter-exchange", "")
                .withArgument("x-dead-letter-routing-key", orderDlq)
                .build();
    }

    @Bean
    public Queue deadLetterQueue() {
        return new Queue(orderDlq);
    }

    @Bean
    public DirectExchange orderExchange() {
        return new DirectExchange(orderExchange);
    }

    @Bean
    public Binding binding(Queue orderQueue, DirectExchange orderExchange) {
        return BindingBuilder.bind(orderQueue)
                .to(orderExchange)
                .with(routingKey);
    }
}
