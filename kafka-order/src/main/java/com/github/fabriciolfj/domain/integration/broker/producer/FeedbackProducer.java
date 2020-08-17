package com.github.fabriciolfj.domain.integration.broker.producer;

import com.github.fabriciolfj.domain.integration.broker.model.FeedbackMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class FeedbackProducer {

    private final KafkaTemplate<String, FeedbackMessage> kafkaTemplate;

    public void publish(final FeedbackMessage feedbackMessage) {
        log.info("Publish message: " + feedbackMessage);
        kafkaTemplate.send("t.commodity.feedback2", feedbackMessage);
    }
}
