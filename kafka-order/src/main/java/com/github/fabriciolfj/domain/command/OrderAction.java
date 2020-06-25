package com.github.fabriciolfj.domain.command;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fabriciolfj.api.request.OrderRequest;
import com.github.fabriciolfj.api.request.OrderRequestMapper;
import com.github.fabriciolfj.domain.entity.Order;
import com.github.fabriciolfj.domain.entity.OrderItem;
import com.github.fabriciolfj.domain.integration.broker.mapper.OrderMessageMapper;
import com.github.fabriciolfj.domain.integration.broker.producer.OrderProducer;
import com.github.fabriciolfj.domain.repository.OrderItemRepository;
import com.github.fabriciolfj.domain.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderAction {

    private final OrderRepository repository;
    private final OrderItemRepository itemRepository;
    private final OrderRequestMapper mapper;
    private final OrderMessageMapper messageMapper;
    private final OrderProducer producer;

    public Order convertToOrder(final OrderRequest request) {
        var order = mapper.toEntiy(request);
        order.setDateTime(LocalDateTime.now());
        order.setCode(RandomStringUtils.randomAlphanumeric(8).toUpperCase());
        return order;
    }

    public void saveToDatabase(final Order order) {
        repository.save(order);
        order.getItems().stream().forEach(i -> {
            i.setOrder(order);
            itemRepository.save(i);
        });
    }

    public void publishToKafka(final OrderItem orderItem) {
        var message = messageMapper.toModel(orderItem);
        producer.send(message);
    }
}
