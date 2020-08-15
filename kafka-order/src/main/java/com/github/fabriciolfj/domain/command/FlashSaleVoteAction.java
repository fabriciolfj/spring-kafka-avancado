package com.github.fabriciolfj.domain.command;

import com.github.fabriciolfj.api.request.FlashSaleVoteRequest;
import com.github.fabriciolfj.domain.integration.broker.model.FlashSaleVoteMessage;
import com.github.fabriciolfj.domain.integration.broker.producer.FlashSaleVoteProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FlashSaleVoteAction {

    @Autowired
    private FlashSaleVoteProducer producer;

    public void publishToKafka(FlashSaleVoteRequest request) {
        var message = new FlashSaleVoteMessage();

        message.setCustomerId(request.getCustomerId());
        message.setItemName(request.getItemName());

        producer.publish(message);
    }

}