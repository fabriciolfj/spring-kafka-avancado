package com.github.fabriciolfj.domain.integration.broker.producer;

import com.github.fabriciolfj.domain.integration.broker.model.DiscountMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DiscountProducer {

    private final KafkaTemplate<String, DiscountMessage> template;

    public void publish(final DiscountMessage message) {
        template.send("t.commodity.promotion", message);
    }
}
