package com.github.fabriciolfj.api.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.github.fabriciolfj.infrastructure.util.LocalDateTimeDeserializer;
import com.github.fabriciolfj.infrastructure.util.LocalDateTimeSerializer;

import java.time.LocalDateTime;

public class WebColorVoteRequest {

	private String color;

	private String username;

	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	private LocalDateTime voteDateTime;

	public String getColor() {
		return color;
	}

	public String getUsername() {
		return username;
	}

	public LocalDateTime getVoteDateTime() {
		return voteDateTime;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setVoteDateTime(LocalDateTime voteDateTime) {
		this.voteDateTime = voteDateTime;
	}

	@Override
	public String toString() {
		return "WebColorVoteRequest [color=" + color + ", voteDateTime=" + voteDateTime + ", username=" + username
				+ "]";
	}

}
