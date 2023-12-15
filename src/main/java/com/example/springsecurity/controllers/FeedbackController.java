package com.example.springsecurity.controllers;

// FeedbackController.java
import com.example.springsecurity.models.Feedback;
import com.example.springsecurity.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/feedback")
@CrossOrigin("*")
public class FeedbackController {
    @Autowired
    private FeedbackService feedbackService;

//    @GetMapping("/{feedbackId}")
//    public Feedback getFeedbackById(@PathVariable int feedbackId) {
//        return feedbackService.getFeedbackById(feedbackId);
//    }


    @GetMapping("/{feedbackId}")
    public List<Feedback> getFeedbackByEmailId(@PathVariable(name = "feedbackId") String feedbackId) {
        return feedbackService.getFeedbackByEmailId(feedbackId);
    }

    @GetMapping("/all")
    public List<Feedback> getAllFeedback() {
        return feedbackService.getAllFeedback();
    }

    @PostMapping("/add")
    public void saveFeedback(@RequestBody Feedback feedback) {
        feedbackService.saveFeedback(feedback);
    }
    // Add other methods as needed
}
