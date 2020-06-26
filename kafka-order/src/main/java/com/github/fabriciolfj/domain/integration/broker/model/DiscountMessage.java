package com.github.fabriciolfj.domain.integration.broker.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiscountMessage {

    private String discountCode;
}
