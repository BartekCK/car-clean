package com.carwash.server.services;

import com.carwash.server.dto.MailDto;
import com.carwash.server.models.User;
import com.carwash.server.models.enums.OrderServiceStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class MailServiceImpl implements MailService {

    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String hostMail;

    @Autowired
    public MailServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public ResponseEntity sendMail(MailDto mailDto, boolean isHtmlContent) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        mimeMessageHelper.setTo(hostMail);
        mimeMessageHelper.setSubject(mailDto.getHeader());
        mimeMessageHelper.setText(mailDto.getMessage(), isHtmlContent);
        javaMailSender.send(mimeMessage);
        return ResponseEntity.ok("Wiadomość została wysłana");
    }

    public void informationAboutServiceStatus(String email, OrderServiceStatus orderServiceStatus) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        mimeMessageHelper.setTo(email);
        mimeMessageHelper.setSubject("Zmiana statusu serwisu");
        mimeMessageHelper.setText("Cześć, \nstatus Twojego serwisu został zmieniony na <b>" +
                orderServiceStatus.toString() + "</b>", true);
        javaMailSender.send(mimeMessage);
    }

    public void informationAboutRegistration(User user) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        mimeMessageHelper.setTo(user.getEmail());
        mimeMessageHelper.setSubject("Założyłeś konto na car-clean.pl");
        mimeMessageHelper.setText("<b>Witamy cię " + user.getUsername() + " w naszym gronie.<br>Już teraz skorzystaj z naszej oferty produktów" +
                " do czyszczenia pojazdu.<br> Oferujemy również profesjonalny detaling samochdów. Przyjedź do nas już dziś i przekonaj się sam.<br>" +
                "Samych sukcesów !</b>", true);
        javaMailSender.send(mimeMessage);
    }


}
