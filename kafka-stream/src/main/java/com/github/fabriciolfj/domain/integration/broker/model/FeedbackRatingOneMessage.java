package com.github.fabriciolfj.domain.integration.broker.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class FeedbackRatingOneMessage {

    private String location;
    private double averageRating;
}
