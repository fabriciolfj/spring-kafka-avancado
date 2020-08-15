package com.github.fabriciolfj.domain.service;

import com.github.fabriciolfj.api.request.FlashSaleVoteRequest;
import com.github.fabriciolfj.domain.command.FlashSaleVoteAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FlashSaleVoteService {

    @Autowired
    private FlashSaleVoteAction action;

    public void createFlashSaleVote(FlashSaleVoteRequest request) {
        action.publishToKafka(request);
    }
}
