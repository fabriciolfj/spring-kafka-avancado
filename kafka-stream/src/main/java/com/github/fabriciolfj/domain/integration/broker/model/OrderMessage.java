package com.github.fabriciolfj.domain.integration.broker.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.github.fabriciolfj.util.LocalDateTimeDeserializer;
import com.github.fabriciolfj.util.LocalDateTimeSerializer;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class OrderMessage implements Serializable {

    private static final long serialVersionUID = 2921365152136307309L;

    private String creditCardNumber;
    private String itemName;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime dateTime;
    private String location;
    private String code;
    private BigDecimal price;
    private Long quantity;

    public OrderMessage copy() {
        var copy = new OrderMessage();

        copy.setCreditCardNumber(this.creditCardNumber);
        copy.setItemName(this.itemName);
        copy.setDateTime(this.dateTime);
        copy.setLocation(this.location);
        copy.setCode(this.code);
        copy.setPrice(this.price);
        copy.setQuantity(this.quantity);

        return copy;
    }
}
