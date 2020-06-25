package com.github.fabriciolfj.broker.message;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@RequiredArgsConstructor
@Service
@Slf4j
public class OrderListener {

    private final ObjectMapper mapper;

    @KafkaListener(topics = "t.commodity.order", groupId = "pattern")
    public void listen(final String json) throws JsonProcessingException {
        var message = mapper.readValue(json, OrderMessage.class);
        var totalItemAmount = message.getPrice().multiply(BigDecimal.valueOf(message.getQuantity()));

        log.info("Processing order {}, item {}, credit card number {}. Total amount for this them is {}",
                message.getCode(), message.getItemName(), message.getCreditCardNumber(), totalItemAmount);
    }
}
