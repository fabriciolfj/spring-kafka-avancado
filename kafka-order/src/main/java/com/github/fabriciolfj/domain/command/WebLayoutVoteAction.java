package com.github.fabriciolfj.domain.command;


import com.github.fabriciolfj.api.request.WebLayoutVoteRequest;
import com.github.fabriciolfj.domain.integration.broker.model.WebLayoutVoteMessage;
import com.github.fabriciolfj.domain.integration.broker.producer.WebLayoutVoteProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WebLayoutVoteAction {

	@Autowired
	private WebLayoutVoteProducer producer;

	public void publishToKafka(WebLayoutVoteRequest request) {
		var message = new WebLayoutVoteMessage();

		message.setUsername(request.getUsername());
		message.setLayout(request.getLayout());
		message.setVoteDateTime(request.getVoteDateTime());

		producer.publish(message);
	}

}
