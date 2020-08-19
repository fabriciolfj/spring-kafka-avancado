package com.github.fabriciolfj.api.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.github.fabriciolfj.infrastructure.util.LocalDateTimeDeserializer;
import com.github.fabriciolfj.infrastructure.util.LocalDateTimeSerializer;

import java.time.LocalDateTime;

public class WebLayoutVoteRequest {

	private String layout;

	private String username;

	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	private LocalDateTime voteDateTime;

	public String getLayout() {
		return layout;
	}

	public String getUsername() {
		return username;
	}

	public LocalDateTime getVoteDateTime() {
		return voteDateTime;
	}

	public void setLayout(String layout) {
		this.layout = layout;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setVoteDateTime(LocalDateTime voteDateTime) {
		this.voteDateTime = voteDateTime;
	}

	@Override
	public String toString() {
		return "WebLayoutVoteRequest [layout=" + layout + ", username=" + username + ", voteDateTime=" + voteDateTime
				+ "]";
	}

}
