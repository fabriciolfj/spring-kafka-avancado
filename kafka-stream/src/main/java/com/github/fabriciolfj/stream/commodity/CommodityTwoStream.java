package com.github.fabriciolfj.stream.commodity;

import com.github.fabriciolfj.domain.integration.broker.model.OrderMessage;
import com.github.fabriciolfj.domain.integration.broker.model.OrderPatternMessage;
import com.github.fabriciolfj.domain.integration.broker.model.OrderRewardMessage;
import com.github.fabriciolfj.util.CommodityStreamUtil;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
//@Configuration
public class CommodityTwoStream {

    //@Bean
    public KStream<String, OrderMessage> kStreamCommodityTrading(StreamsBuilder builder) {
        var stringSerde = Serdes.String();
        var orderSerde = new JsonSerde<>(OrderMessage.class);
        var orderPatternSerde = new JsonSerde<>(OrderPatternMessage.class);
        var orderRewardSerde = new JsonSerde<>(OrderRewardMessage.class);

        KStream<String, OrderMessage> maskedOrderStream = builder.stream("t.commodity.order",
                Consumed.with(stringSerde, orderSerde))
                .mapValues(CommodityStreamUtil::maskCreditCard);

        KStream<String, OrderPatternMessage>[] patternStream = maskedOrderStream
                .mapValues(CommodityStreamUtil::mapToOrderPattern)
                .branch(CommodityStreamUtil.isPlastic(), (k, v) -> true);

        KStream<String, OrderRewardMessage> rewardStream = maskedOrderStream.filter(isLargeQuantity())
                .filterNot(CommodityStreamUtil.isCheap())
                .mapValues(CommodityStreamUtil::mapToOrderReward);

        KStream<String, OrderMessage> storageStream = maskedOrderStream
                .selectKey(CommodityStreamUtil.generateStorageKey());

        int plasticIndex = 0;
        int noPlasticIndex = 1;

        patternStream[plasticIndex].to("t.commodity.pattern-two.plastic", Produced.with(stringSerde, orderPatternSerde));
        patternStream[noPlasticIndex].to("t.commodity.pattern-two.notPlastic", Produced.with(stringSerde, orderPatternSerde));
        rewardStream.to("t.commodity.reward-two", Produced.with(stringSerde, orderRewardSerde));
        storageStream.to("t.commodity.storage-two", Produced.with(stringSerde, orderSerde));

        patternStream[plasticIndex].print(Printed.<String, OrderPatternMessage>toSysOut().withLabel("OrderPatterMessage send plastic"));
        patternStream[noPlasticIndex].print(Printed.<String, OrderPatternMessage>toSysOut().withLabel("OrderPatterMessage send not plastic"));
        rewardStream.print(Printed.<String, OrderRewardMessage>toSysOut().withLabel("OrderRewardMessage send"));
        storageStream.print(Printed.<String, OrderMessage>toSysOut().withLabel("OrderMessage storage send"));
        log.info("Send messages two");
        return maskedOrderStream;
    }
}
