package com.github.fabriciolfj.domain.command;

import com.github.fabriciolfj.api.request.PromotionRequest;
import com.github.fabriciolfj.domain.integration.broker.model.PromotionMessage;
import com.github.fabriciolfj.domain.integration.broker.producer.PromotionProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PromotionAction {

    private final PromotionProducer producer;

    public void publishToKafka(final PromotionRequest request) {
        producer.publish(new PromotionMessage(request.getPromotionCode()));
    }
}
