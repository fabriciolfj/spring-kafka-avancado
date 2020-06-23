package com.github.fabriciolfj.domain.service;

import com.github.fabriciolfj.api.request.OrderRequest;
import com.github.fabriciolfj.domain.command.OrderAction;
import com.github.fabriciolfj.domain.entity.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderAction action;

    public Order saveOrder(OrderRequest request) {
        var order = action.convertToOrder(request);

        action.saveToDatabase(order);

        order.getItems().forEach(action::publishToKafka);
        return order;
    }
}
