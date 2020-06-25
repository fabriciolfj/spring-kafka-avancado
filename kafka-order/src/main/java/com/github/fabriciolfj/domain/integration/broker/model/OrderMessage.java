package com.github.fabriciolfj.domain.integration.broker.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.github.fabriciolfj.infrastructure.util.LocalDateTimeDeserializer;
import com.github.fabriciolfj.infrastructure.util.LocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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
}
