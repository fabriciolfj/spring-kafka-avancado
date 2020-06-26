package com.github.fabriciolfj.domain.integration.broker.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderReplyMessage {

    private String replyMessage;
}
