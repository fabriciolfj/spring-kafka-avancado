package com.github.fabriciolfj.domain.service;

import com.github.fabriciolfj.api.request.PromotionRequest;
import com.github.fabriciolfj.domain.command.PromotionAction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PromotionService {

    private final PromotionAction action;

    public void createPromotion(final PromotionRequest request) {
        action.publishToKafka(request);
    }
}
