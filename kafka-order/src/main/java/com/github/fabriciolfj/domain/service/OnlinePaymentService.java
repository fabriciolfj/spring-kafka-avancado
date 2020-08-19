package com.github.fabriciolfj.domain.service;

import com.github.fabriciolfj.api.request.OnlinePaymentRequest;
import com.github.fabriciolfj.domain.command.OnlinePaymentAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OnlinePaymentService {

	@Autowired
	private OnlinePaymentAction action;

	public void pay(OnlinePaymentRequest request) {
		action.publishPaymentToKafka(request);
	}

}
