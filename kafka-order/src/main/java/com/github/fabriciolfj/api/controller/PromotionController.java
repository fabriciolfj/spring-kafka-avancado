package com.github.fabriciolfj.api.controller;

import com.github.fabriciolfj.api.request.PromotionRequest;
import com.github.fabriciolfj.domain.service.PromotionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/promotion")
public class PromotionController {

    private final PromotionService service;

    @PostMapping
    public ResponseEntity<String> create(@RequestBody final PromotionRequest request) {
        service.createPromotion(request);
        return ResponseEntity.accepted().body(request.getPromotionCode());
    }
}
