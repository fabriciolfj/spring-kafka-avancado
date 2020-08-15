package com.github.fabriciolfj.domain.integration.broker.producer;

import com.github.fabriciolfj.domain.integration.broker.model.FlashSaleVoteMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class FlashSaleVoteProducer {

    @Autowired
    private KafkaTemplate<String, FlashSaleVoteMessage> kafkaTemplate;

    public void publish(final FlashSaleVoteMessage message) {
        kafkaTemplate.send("flashsale-vote", message);
    }
}
