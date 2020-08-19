package com.github.fabriciolfj.domain.service;

import com.github.fabriciolfj.api.request.OnlineOrderRequest;
import com.github.fabriciolfj.domain.command.OnlineOrderAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OnlineOrderService {

	@Autowired
	private OnlineOrderAction action;

	public void saveOnlineOrder(OnlineOrderRequest request) {
		action.publishToKafka(request);
	}

}
