package com.github.fabriciolfj.domain.integration.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fabriciolfj.domain.integration.broker.model.DiscountMessage;
import com.github.fabriciolfj.domain.integration.broker.model.PromotionMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@KafkaListener(topics = "t.commodity.promotion")
@RequiredArgsConstructor
public class PromotionListener {

    private final ObjectMapper mapper;

    @KafkaHandler
    public void listenPromotion(final PromotionMessage message) {
        log.info("Processing promotion: {}", message);
    }

    @KafkaHandler
    public void listenDiscount(final DiscountMessage message) {
        log.info("Processing discount: {}", message);
    }

}
