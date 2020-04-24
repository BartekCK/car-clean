package com.carwash.server.services;

import com.carwash.server.dto.MailDto;
import com.carwash.server.models.User;
import com.carwash.server.models.enums.OrderServiceStatus;
import org.springframework.http.ResponseEntity;

import javax.mail.MessagingException;

public interface MailService {
    ResponseEntity sendMail(MailDto mailDto, boolean isHtmlContent) throws MessagingException;

    void informationAboutServiceStatus(String email, OrderServiceStatus orderServiceStatus) throws MessagingException;

    void informationAboutRegistration(User user) throws MessagingException;
}
