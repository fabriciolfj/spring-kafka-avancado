package com.github.fabriciolfj.domain.service;

import com.github.fabriciolfj.domain.integration.broker.model.FeedbackMessage;
import com.github.fabriciolfj.domain.integration.broker.producer.FeedbackProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class FeedbackService {

    private final FeedbackProducer producer;

    public void send(final FeedbackMessage message) {
        producer.publish(message);
    }

}
