package com.carwash.server.controllers;

import com.carwash.server.dto.MailDto;
import com.carwash.server.services.MailService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;

@CrossOrigin
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
