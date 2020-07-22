package com.github.fabriciolfj.stream.commodity;

import com.github.fabriciolfj.domain.integration.broker.model.OrderMessage;
import com.github.fabriciolfj.domain.integration.broker.model.OrderPatternMessage;
import com.github.fabriciolfj.domain.integration.broker.model.OrderRewardMessage;
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

import static com.github.fabriciolfj.util.CommodityStreamUtil.isLargeQuantity;

@Configuration
public class CommodityOneStream {

    @Bean
    public KStream<String, OrderMessage> kStreamCommodityTrading(StreamsBuilder builder) {
        var stringSerde = Serdes.String();
        var orderSerde = new JsonSerde<>(OrderMessage.class);
        var orderPatternSerde = new JsonSerde<>(OrderPatternMessage.class);
        var orderRewardSerde = new JsonSerde<>(OrderRewardMessage.class);

        KStream<String, OrderMessage> maskedOrderStream = builder.stream("t.commodity.order",
                Consumed.with(stringSerde, orderSerde))
                .mapValues(CommodityStreamUtil::maskCreditCard);

        KStream<String, OrderPatternMessage> patternStream = maskedOrderStream
                .mapValues(CommodityStreamUtil::mapToOrderPattern);
        patternStream.to("t.commodity.pattern-one", Produced.with(stringSerde, orderPatternSerde));

        KStream<String, OrderRewardMessage> rewardStream = maskedOrderStream.filter(isLargeQuantity())
                .mapValues(CommodityStreamUtil::mapToOrderReward);

        rewardStream.to("t.commodity.reward-one", Produced.with(stringSerde, orderRewardSerde));

        maskedOrderStream.to("t.commodity.storage-one", Produced.with(stringSerde, orderSerde));
        maskedOrderStream.print(Printed.<String, OrderMessage>toSysOut().withLabel("Masked Order Stream"));
        return maskedOrderStream;
    }
}
