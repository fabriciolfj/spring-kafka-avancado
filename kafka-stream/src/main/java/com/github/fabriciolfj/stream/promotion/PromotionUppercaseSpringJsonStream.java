package com.github.fabriciolfj.stream.promotion;

import com.github.fabriciolfj.domain.integration.broker.model.PromotionMessage;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Printed;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.serializer.JsonSerde;

//@Configuration
public class PromotionUppercaseSpringJsonStream {

    //@Bean
    public KStream<String, PromotionMessage> kstreamPromotionUppercase(StreamsBuilder builder) {
        var stringSerde = Serdes.String();
        var jsonSerde = new JsonSerde<>(PromotionMessage.class);

        KStream<String, PromotionMessage> sourceStream = builder.stream("t.commodity.promotion", Consumed.with(stringSerde, jsonSerde));
        KStream<String, PromotionMessage> uppercaseStream = sourceStream.mapValues(this::uppercasePromotionCode);

        sourceStream.print(Printed.<String, PromotionMessage>toSysOut().withLabel("JSON Serde Original Stream"));
        uppercaseStream.print(Printed.<String, PromotionMessage>toSysOut().withLabel("Json Serde Uppercase Stream"));

        return sourceStream;
    }

    private PromotionMessage uppercasePromotionCode(PromotionMessage message) {
        return new PromotionMessage(message.getPromotionCode().toUpperCase());
    }
}
