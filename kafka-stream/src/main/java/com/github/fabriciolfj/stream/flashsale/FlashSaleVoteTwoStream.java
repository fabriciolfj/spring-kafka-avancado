package com.github.fabriciolfj.stream.flashsale;

import com.github.fabriciolfj.domain.integration.broker.model.FlashSaleVoteMessage;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.serializer.JsonSerde;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Configuration
public class FlashSaleVoteTwoStream {

    @Bean
    public KStream<String, String> kstreamFlashSaleVote(final StreamsBuilder builder) {
        var stringSerde = Serdes.String();
        var flashSaleVoteSerde = new JsonSerde<>(FlashSaleVoteMessage.class);

        var voteStart = LocalDateTime.of(LocalDate.now(), LocalTime.of(20,0));
        var voteEnd = LocalDateTime.of(LocalDate.now(), LocalTime.of(21,0));

        var flashSaleVoteStream = builder
                .stream("flashsale-vote", Consumed.with(stringSerde, flashSaleVoteSerde))
                .transformValues(() -> new FlashSaleVoteTwoValueTransformer(voteStart, voteEnd))
                .filter((key, transformedValue) -> transformedValue != null)
                .map((key, value) -> KeyValue.pair(value.getCustomerId(), value.getItemName()));
        flashSaleVoteStream.to("flashsale-vote-user-item");

        builder.table("flashsale-vote-user-item", Consumed.with(stringSerde, stringSerde))
                .groupBy((user, votedItem) -> KeyValue.pair(votedItem, votedItem)).count().toStream()
                .to("flashsale-vote-user-item-two-result", Produced.with(stringSerde, Serdes.Long()));

        return flashSaleVoteStream;
    }
}
