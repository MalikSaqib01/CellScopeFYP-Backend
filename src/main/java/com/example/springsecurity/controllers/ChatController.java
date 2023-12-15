package com.example.springsecurity.controllers;


import com.example.springsecurity.models.Message;
import com.example.springsecurity.models.MessageEntity;
import com.example.springsecurity.payload.request.SpecificMessageRequest;
import com.example.springsecurity.repository.MessageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin("*")
@RequestMapping("/chats")
@RestController
public class ChatController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    MessageRepo messageRepo;

    @MessageMapping("/message")
    @SendTo("/chatroom/public")
    public Message receiveMessage(@Payload Message message){

        MessageEntity msg = new MessageEntity();
        msg.setType("public");
        msg.setMessage(message.getMessage());
        msg.setReceiverName(message.getReceiverName());
        msg.setSenderName(message.getSenderName());
        msg.setStatus(message.getStatus());
        msg.setDate(message.getDate());
        messageRepo.save(msg);
        return message;
    }

    @MessageMapping("/private-message")
    public Message recMessage(@Payload Message message){
        simpMessagingTemplate.convertAndSendToUser(message.getReceiverName(),"/private",message);
        MessageEntity msg = new MessageEntity();
        msg.setType("private");
        msg.setMessage(message.getMessage());
        msg.setReceiverName(message.getReceiverName());
        msg.setSenderName(message.getSenderName());
        msg.setStatus(message.getStatus());
        msg.setDate(message.getDate());
        messageRepo.save(msg);
        return message;
    }

    @PostMapping("/search")
    ResponseEntity<List<MessageEntity>> findBySenderNameAndReceiverName(@RequestBody SpecificMessageRequest specificMessageRequest) {
        List<MessageEntity> messageEntityList = messageRepo.findBySenderNameAndReceiverName(
                specificMessageRequest.getSenderName(),
                specificMessageRequest.getReceiverName(),
                specificMessageRequest.getReceiverName(),
                specificMessageRequest.getSenderName()
                );

        return ResponseEntity.status(HttpStatus.OK).body(messageEntityList);
    }


    @GetMapping("/search/{senderName}")
    ResponseEntity<List<MessageEntity>> findBySenderName(@PathVariable("senderName") String senderName) {

        List<MessageEntity> messageEntityList = messageRepo.findBySenderName(senderName);

        return ResponseEntity.status(HttpStatus.FOUND).body(messageEntityList);

    }

}
