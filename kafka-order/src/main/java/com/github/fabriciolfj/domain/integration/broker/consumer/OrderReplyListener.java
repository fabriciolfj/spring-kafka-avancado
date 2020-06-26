package com.github.fabriciolfj.domain.integration.broker.consumer;

import com.github.fabriciolfj.domain.integration.broker.model.OrderReplyMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrderReplyListener {

    @KafkaListener(topics = "t.commodity.order-reply")
    public void listenReply(final OrderReplyMessage message) {
        log.info("Reply message received: {}", message.toString());
    }
}
