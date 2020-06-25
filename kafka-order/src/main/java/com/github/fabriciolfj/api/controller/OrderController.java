package com.github.fabriciolfj.api.controller;

import com.github.fabriciolfj.api.response.OrderResponseMapper;
import com.github.fabriciolfj.domain.service.OrderService;
import com.github.fabriciolfj.api.request.OrderRequest;
import com.github.fabriciolfj.api.response.OrderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/order")
@CrossOrigin(origins = "*")
public class OrderController {

    private final OrderService orderService;
    private final OrderResponseMapper mapper;

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@RequestBody final OrderRequest request) {
        return ResponseEntity.ok().body(mapper
                .toModel(orderService.saveOrder(request))
        );
    }

}
