package com.github.fabriciolfj.domain.integration.broker.mapper;

import com.github.fabriciolfj.domain.entity.OrderItem;
import com.github.fabriciolfj.domain.integration.broker.model.OrderMessage;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@NoArgsConstructor
public abstract class OrderMessageDecorator implements OrderMessageMapper {

    @Autowired
    private OrderMessageMapper messageMapper;


    @Override
    public OrderMessage toModel(OrderItem item) {
        var orderMessage = messageMapper.toModel(item);
        orderMessage.setCode(item.getOrder().getCode());
        orderMessage.setLocation(item.getOrder().getLocation());
        orderMessage.setDateTime(item.getOrder().getDateTime());
        orderMessage.setItemName(item.getName());
        orderMessage.setCreditCardNumber(item.getOrder().getCreditCardNumber());
        return orderMessage;
    }
}
