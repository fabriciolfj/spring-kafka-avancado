package com.github.fabriciolfj.config;

import com.github.fabriciolfj.domain.integration.broker.model.PromotionMessage;

public class PromotionSerde extends CustomJsonSerde<PromotionMessage> {

    public PromotionSerde() {
        super(new CustomJsonSerializer<PromotionMessage>(), new CustomJsonDeserializer<PromotionMessage>(PromotionMessage.class));
    }
}
