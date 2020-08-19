package com.github.fabriciolfj.api.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.github.fabriciolfj.infrastructure.util.LocalDateTimeDeserializer;
import com.github.fabriciolfj.infrastructure.util.LocalDateTimeSerializer;

import java.time.LocalDateTime;

public class OnlinePaymentRequest {

	private String onlineOrderNumber;

	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	private LocalDateTime paymentDateTime;

	private String paymentMethod;

	public String getOnlineOrderNumber() {
		return onlineOrderNumber;
	}

	public LocalDateTime getPaymentDateTime() {
		return paymentDateTime;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setOnlineOrderNumber(String onlineOrderNumber) {
		this.onlineOrderNumber = onlineOrderNumber;
	}

	public void setPaymentDateTime(LocalDateTime paymentDateTime) {
		this.paymentDateTime = paymentDateTime;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	@Override
	public String toString() {
		return "OnlinePaymentRequest [onlineOrderNumber=" + onlineOrderNumber + ", paymentDateTime=" + paymentDateTime
				+ ", paymentMethod=" + paymentMethod + "]";
	}

}
