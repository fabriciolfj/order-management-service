package com.github.fabriciolfj.order_management.configurations;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fabriciolfj.order_management.entrypoint.listener.order.dto.OrderDto;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConversionException;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class CustomJsonMessageConverter extends Jackson2JsonMessageConverter {

    private final ObjectMapper objectMapper;

    public CustomJsonMessageConverter(ObjectMapper objectMapper) {
        super(objectMapper);
        this.objectMapper = objectMapper;
    }

    @Override
    public List<OrderDto> fromMessage(Message message, Object conversionHint) {
        final JavaType javaType = objectMapper
                .getTypeFactory()
                .constructCollectionType(List.class, OrderDto.class);

        try {
            final String json = new String(message.getBody(), StandardCharsets.UTF_8);
            return objectMapper.readValue(json, javaType);
        } catch (IOException e) {
            throw new MessageConversionException("Failed to convert message content", e);
        }
    }
}