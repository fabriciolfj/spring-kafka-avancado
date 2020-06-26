package com.github.fabriciolfj.domain.integration.broker.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PromotionMessage {

    private String promotionCode;
}
