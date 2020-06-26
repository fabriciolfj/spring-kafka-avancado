package com.github.fabriciolfj.domain.integration.broker.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fabriciolfj.domain.integration.broker.model.PromotionMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PromotionProducer {

    private final ObjectMapper mapper;
    private final KafkaTemplate<String, PromotionMessage> kafkaTemplate;

    public void publish(final PromotionMessage message) {
        kafkaTemplate.send("t.commodity.promotion", message);
    }


}
