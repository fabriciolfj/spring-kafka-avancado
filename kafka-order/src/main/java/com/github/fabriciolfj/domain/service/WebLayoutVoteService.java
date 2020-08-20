package com.github.fabriciolfj.domain.service;

import com.github.fabriciolfj.api.request.WebLayoutVoteRequest;
import com.github.fabriciolfj.domain.command.WebLayoutVoteAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WebLayoutVoteService {

	@Autowired
	private WebLayoutVoteAction action;

	public void createLayoutVote(WebLayoutVoteRequest request) {
		action.publishToKafka(request);
	}

}
