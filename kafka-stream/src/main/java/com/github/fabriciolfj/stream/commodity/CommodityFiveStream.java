package com.github.fabriciolfj.stream.commodity;

import com.github.fabriciolfj.domain.integration.broker.model.OrderMessage;
import com.github.fabriciolfj.domain.integration.broker.model.OrderPatternMessage;
import com.github.fabriciolfj.domain.integration.broker.model.OrderRewardMessage;
import com.github.fabriciolfj.util.CommodityStreamUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Printed;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.KafkaStreamBrancher;
import org.springframework.kafka.support.serializer.JsonSerde;

import java.math.BigDecimal;

import static com.github.fabriciolfj.util.CommodityStreamUtil.isLargeQuantity;
import static com.github.fabriciolfj.util.CommodityStreamUtil.isPlastic;

@Slf4j
//@Configuration
public class CommodityFiveStream {

    //@Bean
    public KStream<String, OrderMessage> kStreamCommodityTrading(StreamsBuilder builder) {
        var stringSerde = Serdes.String();
        var orderSerde = new JsonSerde<>(OrderMessage.class);
        var orderPatternSerde = new JsonSerde<>(OrderPatternMessage.class);
        var orderRewardSerde = new JsonSerde<>(OrderRewardMessage.class);

        KStream<String, OrderMessage> maskedOrderStream = builder.stream("t.commodity.order",
                Consumed.with(stringSerde, orderSerde))
                .mapValues(CommodityStreamUtil::maskCreditCard);

        KStream<String, OrderRewardMessage> rewardStream = maskedOrderStream.filter(isLargeQuantity())
                .filterNot(CommodityStreamUtil.isCheap())
                .map(CommodityStreamUtil.mapToOrderRewardChangeKey());

        KStream<String, OrderMessage> storageStream = maskedOrderStream
                .selectKey(CommodityStreamUtil.generateStorageKey());

        final var branchProducer = Produced.with(stringSerde, orderPatternSerde);
        new KafkaStreamBrancher<String, OrderPatternMessage>()
                .branch(isPlastic(), kstream -> kstream.to("t.commodity.pattern-six.plastic", branchProducer))
                .defaultBranch(kstream -> kstream.to("t.commodity.pattern-six.notplastic", branchProducer))
                .onTopOf(maskedOrderStream.mapValues(CommodityStreamUtil::mapToOrderPattern));

        rewardStream.to("t.commodity.reward-six", Produced.with(stringSerde, orderRewardSerde));
        storageStream.to("t.commodity.storage-six", Produced.with(stringSerde, orderSerde));


        rewardStream.print(Printed.<String, OrderRewardMessage>toSysOut().withLabel("OrderRewardMessage send six"));
        storageStream.print(Printed.<String, OrderMessage>toSysOut().withLabel("OrderMessage storage send six"));
        log.info("Send messages five");
        return maskedOrderStream;
    }
}
