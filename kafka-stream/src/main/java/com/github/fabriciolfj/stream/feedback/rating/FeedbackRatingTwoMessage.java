package com.github.fabriciolfj.stream.feedback.rating;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;
import java.util.TreeMap;

@Getter
@Setter
@ToString
public class FeedbackRatingTwoMessage {

    private String location;
    private double averageRating;
    private Map<Integer, Long> ratingMap = new TreeMap<>();
}
