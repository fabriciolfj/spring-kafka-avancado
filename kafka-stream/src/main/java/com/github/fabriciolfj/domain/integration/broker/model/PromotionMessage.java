package com.github.fabriciolfj.domain.integration.broker.model;


public class PromotionMessage {

    private String promotionCode;

    public PromotionMessage() {

    }

    public PromotionMessage(String promotionCode) {
        this.promotionCode = promotionCode;
    }

    public String getPromotionCode() {
        return promotionCode;
    }

    public void setPromotionCode(String promotionCode) {
        this.promotionCode = promotionCode;
    }

    @Override
    public String toString() {
        return "PromotionMessage{" +
                "promotionCode='" + promotionCode + '\'' +
                '}';
    }
}
