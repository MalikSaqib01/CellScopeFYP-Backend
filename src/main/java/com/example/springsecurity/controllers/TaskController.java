package com.example.springsecurity.controllers;


import com.example.springsecurity.models.Task;
import com.example.springsecurity.payload.request.AddTaskRequest;
import com.example.springsecurity.payload.response.TaskResponse;
import com.example.springsecurity.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/tasks")
@CrossOrigin("*")
public class TaskController {

    @Autowired
    private TaskRepository repository;

    @GetMapping("/all")
    public ResponseEntity<List<TaskResponse>> getTasks() {
        List<Task> tasks = repository.findAll();
        List<TaskResponse> responses = new ArrayList<>();
        for (Task task : tasks) {
            TaskResponse response = new TaskResponse(task.getId(), task.getDescription(), task.getDayAndTime(), task.isSetReminder());
            responses.add(response);
        }
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponse> getTaskById(@PathVariable Long id) {
        Optional<Task> task = repository.findById(id);
        if(task == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(new TaskResponse(task.get().getId(), task.get().getDescription(), task.get().getDayAndTime(), task.get().isSetReminder()));
    }

    @GetMapping("specific/{email}")
    public ResponseEntity<List<Task>> getTaskByEmail(@PathVariable String email) {
        List<Task> task = repository.findByEmail(email);

        if(task == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }else {
            return ResponseEntity.ok(task);
        }

    }

    @PostMapping("/add/{email}")
    public ResponseEntity<TaskResponse> addTask(@PathVariable("email") String email,@RequestBody AddTaskRequest addTaskRequest) {
        Task task = new Task();
        task.setDescription(addTaskRequest.getText());
        task.setDayAndTime(addTaskRequest.getDay()+ " " +addTaskRequest.getTime());
        task.setEmail(email);
        task.setSetReminder(addTaskRequest.isReminder());

        System.out.println(task.getDayAndTime());









        repository.save(task);
        return ResponseEntity.ok(new TaskResponse(task.getId(), task.getDescription(), task.getDayAndTime(), task.isSetReminder()));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        repository.deleteById(id);

        return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY)
                .header("Location", "/").build();
    }








}

