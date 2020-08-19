package com.github.fabriciolfj.domain.integration.broker.producer;


import com.github.fabriciolfj.domain.integration.broker.model.OnlineOrderMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class OnlineOrderProducer {

	@Autowired
	private KafkaTemplate<String, OnlineOrderMessage> kafkaTemplate;

	public void publish(OnlineOrderMessage message) {
		kafkaTemplate.send("t.commodity.online-order", message.getOnlineOrderNumber(), message);
	}

}
