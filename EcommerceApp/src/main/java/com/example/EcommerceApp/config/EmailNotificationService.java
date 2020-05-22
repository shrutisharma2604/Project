package com.example.EcommerceApp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailNotificationService {
    @Autowired
    private JavaMailSender javaMailSender;

    /**
     * This method is used to send the email service
     * @param subject
     * @param text
     * @param sendTo
     */
    @Async
    public void sendNotification(String subject,String text,String sendTo){
        SimpleMailMessage mail=new SimpleMailMessage();
        mail.setTo(sendTo);
        mail.setFrom("ss1482004@gmail.com");
        mail.setSubject(subject);
        mail.setText(text);
        javaMailSender.send(mail);
    }

}
