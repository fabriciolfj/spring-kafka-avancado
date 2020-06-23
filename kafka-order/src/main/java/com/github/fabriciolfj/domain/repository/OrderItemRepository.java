package com.github.fabriciolfj.domain.repository;

import com.github.fabriciolfj.domain.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
