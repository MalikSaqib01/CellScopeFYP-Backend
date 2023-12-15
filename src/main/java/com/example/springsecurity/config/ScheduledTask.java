//package com.example.springsecurity.config;
//
//
//import com.example.springsecurity.models.Task;
//import com.example.springsecurity.repository.TaskRepository;
//import com.example.springsecurity.service.MailService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.mail.SimpleMailMessage;
//import org.springframework.scheduling.annotation.EnableScheduling;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import java.sql.Time;
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Component
//@EnableScheduling
//public class ScheduledTask {
//
//
//    @Autowired
//    MailService mailService;
//
//    @Autowired
//    TaskRepository taskRepository ;
//
//    @Scheduled(fixedRate = 60000)
//    public void sendScheduledEmails() {
//
//        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
//        simpleMailMessage.setFrom("malicksaqib01@gmail.com");
//        simpleMailMessage.setSubject("REMINDER");
//
////        List<Task> tasks =  taskRepository.findAll();
////        LocalDate currentDate = LocalDate.now();
////
////        System.out.println(currentDate);
////
////
////
////        List<Task> tasksWithCurrentDate = tasks.stream()
////                .filter(task -> {
////                    // Assuming dayAndTime is in the format "yyyy-MM-dd HH:mm:ss"
////                    LocalDate taskDate = LocalDate.parse(task.getDayAndTime().substring(0, 10));
////                    return currentDate.isEqual(taskDate);
////                })
////                .collect(Collectors.toList());
//
//
//
//        List<Task> tasks = taskRepository.findAll();
//        LocalDateTime currentDateTime = LocalDateTime.now();
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm");
//        String formattedCurrentDateTime = currentDateTime.format(formatter);
//
//        System.out.println("System time: "+ formattedCurrentDateTime);
//
//
//        List<Task> tasksWithTime = tasks.stream()
//                .filter(task -> {
//                    // Print the value for debugging
//                    System.out.println("Original value: " + task.getDayAndTime());
//                    System.out.println("System time: "+ formattedCurrentDateTime);
//                    System.out.println(formattedCurrentDateTime.equals(task.getDayAndTime()));
//
//
//                    // Assuming dayAndTime is in the format "yyyy-MM-dd HH:mm"
////                    LocalDateTime taskDateTime = LocalDateTime.parse(task.getDayAndTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
//                    return formattedCurrentDateTime.equals(task.getDayAndTime());
//                })
//                .collect(Collectors.toList());
//
//        System.out.println(tasksWithTime.size());
//
//
//
//
//        for (Task task : tasksWithTime) {
//          if(task.isSetReminder()) {
//              simpleMailMessage.setTo(task.getEmail());
//              simpleMailMessage.setText(task.getDescription());
//              mailService.sendMessage(simpleMailMessage);
//
//          }
//        }
//
//
//
//
//
//
//
//
//
//
//    }
//
//}
