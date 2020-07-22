package com.github.fabriciolfj.util;

import com.github.fabriciolfj.domain.integration.broker.model.OrderMessage;
import com.github.fabriciolfj.domain.integration.broker.model.OrderPatternMessage;
import com.github.fabriciolfj.domain.integration.broker.model.OrderRewardMessage;
import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.streams.kstream.Predicate;
import org.springframework.core.annotation.Order;

import java.math.BigDecimal;

public class CommodityStreamUtil {

    public static OrderMessage maskCreditCard(OrderMessage original) {
        var converted = original.copy();
        var maskedCreditCardNumber = original.getCreditCardNumber().replaceFirst("\\d{12}",
                StringUtils.repeat('*', 12));
        converted.setCreditCardNumber(maskedCreditCardNumber);
        return converted;
    }

    public static OrderPatternMessage mapToOrderPattern(OrderMessage original) {
        var result = new OrderPatternMessage();

        result.setItemName(original.getItemName());
        result.setOrderDateTime(original.getDateTime());
        result.setOrderLocation(original.getLocation());
        result.setOrderNumber(original.getCode());

        var totalItemAmount = original.getPrice().multiply(BigDecimal.valueOf(original.getQuantity()));
        result.setTotalItemAmount(totalItemAmount);

        return result;
    }

    public static OrderRewardMessage mapToOrderReward(OrderMessage original) {
        var result = new OrderRewardMessage();

        result.setItemName(original.getItemName());
        result.setDateTime(original.getDateTime());
        result.setLocation(original.getLocation());
        result.setCode(original.getCode());
        result.setPrice(original.getPrice());
        result.setQuantity(original.getQuantity());

        return result;
    }

    public static Predicate<String, OrderMessage> isLargeQuantity() {
        return (key, value) -> value.getQuantity() > 200;
    }
}
