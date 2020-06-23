package com.github.fabriciolfj.api.request;

import com.github.fabriciolfj.domain.entity.Order;
import org.mapstruct.Mapper;

@Mapper
public interface OrderRequestMapper {

    Order toEntiy(final OrderRequest request);
}
