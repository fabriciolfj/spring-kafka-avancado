package com.github.fabriciolfj.domain.integration.broker.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.github.fabriciolfj.util.LocalDateTimeDeserializer;
import com.github.fabriciolfj.util.LocalDateTimeSerializer;

import java.time.LocalDateTime;

public class OnlinePaymentMessage {

	private String onlineOrderNumber;

	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	private LocalDateTime paymentDateTime;

	private String paymentMethod;
	private String paymentNumber;

	public String getOnlineOrderNumber() {
		return onlineOrderNumber;
	}

	public LocalDateTime getPaymentDateTime() {
		return paymentDateTime;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public String getPaymentNumber() {
		return paymentNumber;
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

	public void setPaymentNumber(String paymentNumber) {
		this.paymentNumber = paymentNumber;
	}

	@Override
	public String toString() {
		return "OnlinePaymentMessage [onlineOrderNumber=" + onlineOrderNumber + ", paymentDateTime=" + paymentDateTime
				+ ", paymentMethod=" + paymentMethod + ", paymentNumber=" + paymentNumber + "]";
	}

}
