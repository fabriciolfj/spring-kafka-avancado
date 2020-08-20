package com.github.fabriciolfj.domain.service;

import com.github.fabriciolfj.api.request.WebColorVoteRequest;
import com.github.fabriciolfj.domain.command.WebColorVoteAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WebColorVoteService {

	@Autowired
	private WebColorVoteAction action;

	public void createColorVote(WebColorVoteRequest request) {
		action.publishToKafka(request);
	}

}
