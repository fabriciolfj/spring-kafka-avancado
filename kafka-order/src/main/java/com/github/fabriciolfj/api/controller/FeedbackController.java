package com.github.fabriciolfj.api.controller;

import com.github.fabriciolfj.domain.integration.broker.model.FeedbackMessage;
import com.github.fabriciolfj.domain.service.FeedbackService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/feedback")
@CrossOrigin(origins = "*")
public class FeedbackController {

    private final FeedbackService service;

    @PostMapping
    public ResponseEntity<?> send(@RequestBody final FeedbackMessage feedbackMessage) {
        service.send(feedbackMessage);
        return ResponseEntity.noContent().build();
    }
}
