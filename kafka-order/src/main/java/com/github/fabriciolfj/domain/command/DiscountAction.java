package com.github.fabriciolfj.domain.command;

import com.github.fabriciolfj.domain.integration.broker.model.DiscountMessage;
import com.github.fabriciolfj.domain.integration.broker.model.DiscountRequest;
import com.github.fabriciolfj.domain.integration.broker.producer.DiscountProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class DiscountAction {

    private final DiscountProducer producer;

    public void publishToKafka(final DiscountRequest request) {
        producer.publish(new DiscountMessage(request.getDiscountCode()));
    }
}
