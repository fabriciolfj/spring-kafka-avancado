package com.github.fabriciolfj.util;

import com.github.fabriciolfj.domain.integration.broker.model.OrderMessage;
import com.github.fabriciolfj.domain.integration.broker.model.OrderPatternMessage;
import com.github.fabriciolfj.domain.integration.broker.model.OrderRewardMessage;
import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.KeyValueMapper;
import org.apache.kafka.streams.kstream.Named;
import org.apache.kafka.streams.kstream.Predicate;
import org.springframework.core.annotation.Order;

import java.math.BigDecimal;
import java.util.Base64;

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

    public static Predicate<String, OrderPatternMessage>  isPlastic() {
        return (key, value) -> StringUtils.startsWithIgnoreCase(value.getItemName(), "Plastic");
    }

    public static KeyValueMapper<String, OrderMessage, String> generateStorageKey() {
        return (key, value) -> Base64.getEncoder().encodeToString(value.getCode().getBytes());
    }

    public static Predicate<? super String, ? super OrderMessage> isCheap() {
        return (key, value) -> value.getPrice().compareTo(new BigDecimal(100)) == 1;
    }

    public static KeyValueMapper<String, OrderMessage, KeyValue<String,OrderRewardMessage>> mapToOrderRewardChangeKey() {
        return ((key, value) -> KeyValue.pair(value.getLocation(), mapToOrderReward(value)));
    }
}
