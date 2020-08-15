package com.github.fabriciolfj.stream.promotion;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fabriciolfj.domain.integration.broker.model.PromotionMessage;
import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Printed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;

//@Configuration
public class PromotionUppercaseStream {

    private static final Logger LOG = LoggerFactory.getLogger(PromotionUppercaseStream.class);

    public ObjectMapper objectMapper = new ObjectMapper();

    //@Bean
    public KStream<String, String> kStreamPromotionUppercase(StreamsBuilder builder) {
        var stringSerde = Serdes.String();

        KStream<String, String> sourceStream = builder.stream("t.commodity.promotion", Consumed.with(stringSerde, stringSerde));
        KStream<String, String> uppercaseStream = sourceStream.mapValues(this::uppercasePromotionCode);

        uppercaseStream.to("t.commodity.promotion-uppercase");

        sourceStream.print(Printed.<String, String>toSysOut().withLabel("Json original"));
        uppercaseStream.print(Printed.<String, String>toSysOut().withLabel("Json uppercase stream"));

        return sourceStream;
    }

    private String uppercasePromotionCode(String message) {
        try {
            var original = objectMapper.readValue(message, PromotionMessage.class);
            var converted = new PromotionMessage(original.getPromotionCode().toUpperCase());
            return objectMapper.writeValueAsString(converted);
        } catch (JsonProcessingException e) {
            LOG.error(e.getMessage());
        }

        return StringUtils.EMPTY;
    }
}
