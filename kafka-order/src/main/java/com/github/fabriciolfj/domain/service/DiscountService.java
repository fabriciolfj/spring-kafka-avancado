package com.github.fabriciolfj.domain.service;

import com.github.fabriciolfj.domain.command.DiscountAction;
import com.github.fabriciolfj.domain.integration.broker.model.DiscountRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DiscountService {

    private final DiscountAction action;

    public void publisher(final DiscountRequest request) {
        action.publishToKafka(request);
    }
}
