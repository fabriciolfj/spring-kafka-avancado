package com.github.fabriciolfj.api.controller;

import com.github.fabriciolfj.domain.integration.broker.model.DiscountRequest;
import com.github.fabriciolfj.domain.service.DiscountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/discount")
public class DiscountController {

    private final DiscountService service;

    @PostMapping
    public ResponseEntity<String> create(@RequestBody final DiscountRequest request) {
        service.publisher(request);
        return ResponseEntity.accepted().body(request.getDiscountCode());
    }
}
