package com.github.fabriciolfj.stream.commodity;


import com.github.fabriciolfj.domain.integration.broker.model.OrderMessage;
import com.github.fabriciolfj.util.CommodityStreamUtil;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Printed;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.serializer.JsonSerde;

//@Configuration
public class MaskOrderStream {

    @Bean
    public KStream<String, OrderMessage> kstreamCommodityTrading(StreamsBuilder builder) {
        var stringSerde = Serdes.String();
        var orderSerde = new JsonSerde<>(OrderMessage.class);

        KStream<String, OrderMessage> maskedOrderStream = builder.stream("t.commodity.order", Consumed.with(stringSerde, orderSerde))
                .mapValues(CommodityStreamUtil::maskCreditCard);

        maskedOrderStream.to("to.commodity.order-masked", Produced.with(stringSerde, orderSerde));
        maskedOrderStream.print(Printed.<String, OrderMessage>toSysOut().withLabel("Masked Order Stream"));

        return maskedOrderStream;
    }
}