package com.example.springsecurity.repository;

import com.example.springsecurity.models.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface FeedbackRepository extends JpaRepository <Feedback , Integer>{


   @Query
    List<Feedback> findByEmail(String feedbackId);
}
