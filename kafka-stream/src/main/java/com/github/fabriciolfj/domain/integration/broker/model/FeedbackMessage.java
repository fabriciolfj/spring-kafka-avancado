package com.github.fabriciolfj.domain.integration.broker.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.github.fabriciolfj.util.LocalDateTimeDeserializer;
import com.github.fabriciolfj.util.LocalDateTimeSerializer;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FeedbackMessage {

    private String feedback;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime feedbackDateTime;
    private String location;
    private int rating;
}
