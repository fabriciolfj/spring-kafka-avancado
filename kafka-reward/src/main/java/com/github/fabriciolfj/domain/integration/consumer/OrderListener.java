package com.github.fabriciolfj.domain.integration.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fabriciolfj.domain.integration.broker.model.OrderMessage;
import com.github.fabriciolfj.domain.integration.broker.model.OrderReplyMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@RequiredArgsConstructor
@Slf4j
@Service
public class OrderListener {

    private final ObjectMapper mapper;
    private final KafkaTemplate<String, OrderReplyMessage> template;

    @KafkaListener(topics = "t.commodity.order")
    public void listen(final ConsumerRecord<String, OrderMessage> consumerRecord) {
        var headers = consumerRecord.headers();
        var orderMessage = consumerRecord.value();
        log.info("Processing order {}, item {}, credit card number {}", orderMessage.getCode(), orderMessage.getItemName(), orderMessage.getCreditCardNumber());
        headers.forEach(h -> log.info("key: {}, value: {}", h.key(), new String(h.value())));

        var bonusPercentage = Double.parseDouble(new String(headers.lastHeader("surpriseBonus").value()));
        var bonusAmount = new BigDecimal(bonusPercentage / 100).multiply(orderMessage.getPrice().multiply(new BigDecimal(orderMessage.getQuantity())));
        log.info("Surprise bonus is {}", bonusAmount);

        OrderReplyMessage replyMessage = new OrderReplyMessage("test");
        template.send("t.commodity.order-reply", replyMessage);
    }
}
