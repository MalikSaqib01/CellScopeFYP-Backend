package com.example.springsecurity.service;

// FeedbackServiceImpl.java
import com.example.springsecurity.models.Feedback;
import com.example.springsecurity.repository.FeedbackRepository;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class FeedbackService {
    @Autowired
    private FeedbackRepository feedbackRepository;

    public Feedback getFeedbackById(int feedbackId) {
        return feedbackRepository.findById(feedbackId).orElse(null);
    }

    public List<Feedback> getFeedbackByEmailId(String feedbackId) {
        return feedbackRepository.findByEmail(feedbackId);
    }


    public List<Feedback> getAllFeedback() {
        return feedbackRepository.findAll();
    }


    public void saveFeedback(Feedback feedback) {

        Date date = new Date();

        String dateString = date.toString();

       // String dateString = "Mon Dec 04 13:01:00 PKT 2023";

        // Parse the input date string
        Date parsedDate = parseDateString(dateString);

        // Format the date in a way MySQL understands
        String formattedDate = formatToMySQLDateTime(parsedDate);
        feedback.setDate(formattedDate);
        feedbackRepository.save(feedback);
    }

    private static String extractDate(String formattedDate) {
        // Define the regex pattern to match the date portion
        Pattern pattern = Pattern.compile("\\b\\d{4}-\\d{2}-\\d{2}\\b");

        // Create a matcher object
        Matcher matcher = pattern.matcher(formattedDate);

        // Check if the pattern is found
        if (matcher.find()) {
            // Extract and return the matched date portion
            return matcher.group();
        } else {
            // If no match is found, return an empty string or handle accordingly
            return "";
        }
    }

    private static Date parseDateString(String dateString) {
        SimpleDateFormat inputFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");

        try {
            return inputFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            // Handle the exception or throw it as needed
            return null;
        }
    }

    private static String formatToMySQLDateTime(Date date) {
        SimpleDateFormat mysqlFormat = new SimpleDateFormat("yyyy-MM-dd");

        return mysqlFormat.format(date);
    }
}

