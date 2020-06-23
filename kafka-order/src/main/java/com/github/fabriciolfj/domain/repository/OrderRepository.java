package com.github.fabriciolfj.domain.repository;

import com.github.fabriciolfj.domain.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
