package com.example.springsecurity.controllers;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.simpleemail.model.Message;
import com.amazonaws.services.simpleemail.model.SendEmailRequest;
import com.example.springsecurity.service.MailService;
import com.stripe.param.ChargeCreateParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.amazonaws.services.ec2.model.NetworkInterfaceAttribute.Attachment;
import static com.amazonaws.services.ec2.model.Scope.Region;

@RestController
@RequestMapping("/api/v1")
public class EmailController {

    @Autowired
    private MailService mailService;

    @PostMapping("/sendNotification")
    public String sendNotification(@RequestParam String fromEmail,
                                   @RequestParam String toemail,
                                   @RequestParam String subject,
                                   @RequestParam String body  ) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(fromEmail);
        simpleMailMessage.setTo(toemail);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(body);
        mailService.sendMessage(simpleMailMessage);

        return "mail sent success";
    }






}



