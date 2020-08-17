package com.github.fabriciolfj.stream.feedback.rating;

import com.github.fabriciolfj.domain.integration.broker.model.FeedbackMessage;
import com.github.fabriciolfj.domain.integration.broker.model.FeedbackRatingOneMessage;
import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.streams.kstream.ValueTransformer;
import org.apache.kafka.streams.processor.ProcessorContext;
import org.apache.kafka.streams.state.KeyValueStore;

import java.util.Optional;

public class FeedbackRatingOneTransformer implements ValueTransformer<FeedbackMessage, FeedbackRatingOneMessage> {

    private ProcessorContext context;
    private final String stateStoreName;
    private KeyValueStore<String, FeedbackRatingOneStoreValue> ratingStateStore;

    public FeedbackRatingOneTransformer(final String stateStoreName) {
        if (StringUtils.isEmpty(stateStoreName)) {
            throw new IllegalArgumentException("State store name must not empty");
        }

        this.stateStoreName = stateStoreName;
    }

    @Override
    public void init(ProcessorContext context) {
        this.context = context;
        this.ratingStateStore = (KeyValueStore<String, FeedbackRatingOneStoreValue>) this.context.getStateStore(this.stateStoreName);
    }

    @Override
    public FeedbackRatingOneMessage transform(FeedbackMessage feedbackMessage) {
        var storeValue = Optional.ofNullable(ratingStateStore.get(feedbackMessage.getLocation()))
                .orElse(new FeedbackRatingOneStoreValue());

        var newSumRating = storeValue.getSumRating() + feedbackMessage.getRating();
        storeValue.setSumRating(newSumRating);
        var newCountRating = storeValue.getCountRating() + 1;
        storeValue.setCountRating(newCountRating);

        ratingStateStore.put(feedbackMessage.getLocation(), storeValue);

        var branchRating =new FeedbackRatingOneMessage();
        branchRating.setLocation(feedbackMessage.getLocation());
        double averageRating = Math.round((double) newSumRating / newCountRating * 10d) / 10d;
        branchRating.setAverageRating(averageRating);

        return branchRating;
    }

    @Override
    public void close() {

    }
}
