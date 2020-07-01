package com.github.fabriciolfj.broker.message;


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
}
