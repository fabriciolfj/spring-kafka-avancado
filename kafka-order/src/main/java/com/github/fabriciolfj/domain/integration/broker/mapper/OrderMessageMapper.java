package com.github.fabriciolfj.domain.integration.broker.mapper;

import com.github.fabriciolfj.domain.entity.OrderItem;
import com.github.fabriciolfj.domain.integration.broker.model.OrderMessage;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;

@Mapper
@DecoratedWith(OrderMessageDecorator.class)
public interface OrderMessageMapper {

    OrderMessage toModel(final OrderItem item);
}
