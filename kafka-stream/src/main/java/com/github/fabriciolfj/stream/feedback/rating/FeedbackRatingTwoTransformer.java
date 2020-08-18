package com.github.fabriciolfj.stream.feedback.rating;

import com.github.fabriciolfj.domain.integration.broker.model.FeedbackMessage;
import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.streams.kstream.ValueTransformer;
import org.apache.kafka.streams.processor.ProcessorContext;
import org.apache.kafka.streams.state.KeyValueStore;

import javax.swing.text.html.Option;
import java.util.Map;
import java.util.Optional;

public class FeedbackRatingTwoTransformer implements ValueTransformer<FeedbackMessage, FeedbackRatingTwoMessage> {

    private ProcessorContext context;
    private final String stateStoreName;
    private KeyValueStore<String, FeedbackRatingTwoStoreValue> ratingStateStore;

    public FeedbackRatingTwoTransformer(String stateStoreName){
        if(StringUtils.isEmpty(stateStoreName)) {
            throw new IllegalArgumentException("State store name must not empty");
        }

        this.stateStoreName = stateStoreName;

    }
    @Override
    public void init(ProcessorContext context) {
        this.context = context;
        this.ratingStateStore = (KeyValueStore<String, FeedbackRatingTwoStoreValue>) this.context.getStateStore(this.stateStoreName);
    }

    @Override
    public FeedbackRatingTwoMessage transform(FeedbackMessage value) {
        var storeValue = Optional.ofNullable(ratingStateStore.get(value.getLocation()))
                .orElse(new FeedbackRatingTwoStoreValue());

        var rating = storeValue.getRatingMap();

        var currentRatingCount = Optional.ofNullable(rating.get(value.getRating())).orElse(0L);
        var newRatingCount = currentRatingCount + 1;
        rating.put(value.getRating(), newRatingCount);
        ratingStateStore.put(value.getLocation(), storeValue);

        var branchRating = new FeedbackRatingTwoMessage();
        branchRating.setLocation(value.getLocation());
        branchRating.setRatingMap(rating);
        branchRating.setAverageRating(caculateAverage(rating));

        return branchRating;
    }

    private double caculateAverage(Map<Integer, Long> rating) {
        var sumRating = 0;
        var countRating = 0;

        for (var entry : rating.entrySet()) {
            sumRating += entry.getKey() * entry.getValue();
            countRating += entry.getValue();
        }

        return Math.round((double) sumRating / countRating * 10d) / 10d;
    }

    @Override
    public void close() {

    }
}
