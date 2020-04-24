package com.carwash.server.controllers;

import com.carwash.server.dto.MailDto;
import com.carwash.server.services.MailService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;

@RestController
@RequestMapping("api/v1/mails")
@AllArgsConstructor
public class MailController {

    private final MailService mailService;

    @PostMapping
    public ResponseEntity sendMail(@RequestBody MailDto mailDto) throws MessagingException {
        return mailService.sendMail(mailDto, false);
    }
}
