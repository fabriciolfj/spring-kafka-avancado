package com.github.fabriciolfj.domain.integration.consumer;

import com.github.fabriciolfj.domain.integration.broker.model.PromotionMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PromotionUppercaseListener {

    @KafkaListener(topics = "t.commodity.promotion-uppercase")
    public void listenPromotion(PromotionMessage message) {
        log.info("Processing uppercase promotion : {}", message);
    }

}
