package com.github.fabriciolfj.api.response;

import com.github.fabriciolfj.domain.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface OrderResponseMapper {

    @Mapping(target = "orderNumber", source = "code")
    OrderResponse toModel(final Order order);
}
