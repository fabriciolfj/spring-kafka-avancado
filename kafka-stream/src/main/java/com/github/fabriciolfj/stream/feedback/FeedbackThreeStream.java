package com.github.fabriciolfj.stream.feedback;

import com.github.fabriciolfj.domain.integration.broker.model.FeedbackMessage;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.serializer.JsonSerde;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

//@Configuration
public class FeedbackThreeStream {

    private static final Set<String> BAD_WORDS = Set.of("angry", "sad", "bad");
    private static final Set<String> GOOD_WORDS = Set.of("happy", "good", "helpful");

    //@Bean
    public KStream<String, FeedbackMessage> kstreamFeedBack(StreamsBuilder builder) {
        var stringSerde = Serdes.String();
        var feedbackSerde = new JsonSerde<>(FeedbackMessage.class);

        var sourceStream = builder.stream("t.commodity.feedback", Consumed.with(stringSerde, feedbackSerde));
        var feedbackStreams = sourceStream.flatMap(splitWords()).branch(isGoodWord(), isBadWord());

        feedbackStreams[0].to("t.commodity.feedback-three-good");
        feedbackStreams[1].to("t.commodity.feedback-three-bad");

        return sourceStream;
    }

    private Predicate<? super String, ? super String> isBadWord() {
        return ((key, value) -> BAD_WORDS.contains(value));
    }

    private Predicate<? super String, ? super String> isGoodWord() {
        return ((key, value) -> GOOD_WORDS.contains(value));
    }

    private KeyValueMapper<String, FeedbackMessage, Iterable<KeyValue<String, String>>> splitWords() {
        return (key, value) -> Arrays
                .asList(value.getDescription().replaceAll("[^a-zA-Z ]", "").toLowerCase().split("\\s+")).stream()
                .distinct().map(word -> KeyValue.pair(value.getDescription(), word)).collect(Collectors.toList());
    }
}
