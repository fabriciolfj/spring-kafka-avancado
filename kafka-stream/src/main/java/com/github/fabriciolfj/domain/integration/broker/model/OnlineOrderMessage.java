package com.github.fabriciolfj.domain.integration.broker.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.github.fabriciolfj.util.LocalDateTimeDeserializer;
import com.github.fabriciolfj.util.LocalDateTimeSerializer;

import java.time.LocalDateTime;

public class OnlineOrderMessage {

	private String onlineOrderNumber;

	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	private LocalDateTime orderDateTime;

	private int totalAmount;
	private String username;

	public String getOnlineOrderNumber() {
		return onlineOrderNumber;
	}

	public LocalDateTime getOrderDateTime() {
		return orderDateTime;
	}

	public int getTotalAmount() {
		return totalAmount;
	}

	public String getUsername() {
		return username;
	}

	public void setOnlineOrderNumber(String onlineOrderNumber) {
		this.onlineOrderNumber = onlineOrderNumber;
	}

	public void setOrderDateTime(LocalDateTime orderDateTime) {
		this.orderDateTime = orderDateTime;
	}

	public void setTotalAmount(int totalAmount) {
		this.totalAmount = totalAmount;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String toString() {
		return "OnlineOrderMessage [onlineOrderNumber=" + onlineOrderNumber + ", orderDateTime=" + orderDateTime
				+ ", totalAmount=" + totalAmount + ", username=" + username + "]";
	}

}
