package com.github.fabriciolfj.domain.integration.broker.producer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fabriciolfj.domain.integration.broker.model.OrderMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderProducer {

    private final KafkaTemplate<String, OrderMessage> kafkaTemplate;

    public void send(final OrderMessage message) {
        kafkaTemplate.send("t.commodity.order", message.getCode(), message)
                .addCallback(new ListenableFutureCallback<>() {
                    @Override
                    public void onFailure(Throwable ex) {
                        log.error("ORder {}, item {} failed to publish, because {}", message.getCode(), message.getItemName(), ex.getMessage());
                    }

                    @Override
                    public void onSuccess(SendResult<String, OrderMessage> result) {
                        log.info("Order {}, item {} published successfuly", message.getCode(), message.getItemName());
                    }
                });

        log.info("Just a dummy message for order {}, item {} published successfuly", message.getCode(), message.getItemName());
    }
}
