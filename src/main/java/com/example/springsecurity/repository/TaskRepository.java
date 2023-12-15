package com.example.springsecurity.repository;

import com.example.springsecurity.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query
    List<Task> findByEmail(String email);

}
