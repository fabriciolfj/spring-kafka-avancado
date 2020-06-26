package com.github.fabriciolfj.domain.integration.broker.producer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fabriciolfj.domain.integration.broker.model.OrderMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderProducer {

    private final KafkaTemplate<String, OrderMessage> kafkaTemplate;

    private ProducerRecord<String, OrderMessage> buildProducerRecord(final OrderMessage message) {
        int surpriseBonus = StringUtils.startsWithIgnoreCase(message.getLocation(), "A") ? 15 : 15;

        List<Header> headers = new ArrayList<>();
        var surpriseBonusHeader = new RecordHeader("surpriseBonus", Integer.toString(surpriseBonus).getBytes());
        headers.add(surpriseBonusHeader);

        return new ProducerRecord<String, OrderMessage>("t.commodity.order", null,  message.getCode(), message, headers);
    }

    public void send(final OrderMessage message) {
        var producerRecord = buildProducerRecord(message);
        kafkaTemplate.send(producerRecord)
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
