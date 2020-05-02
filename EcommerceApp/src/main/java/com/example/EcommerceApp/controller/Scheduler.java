package com.example.EcommerceApp.controller;

import com.example.EcommerceApp.config.EmailNotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
public class Scheduler {
    @Autowired
    EmailNotificationService emailNotificationService;

    Logger logger= LoggerFactory.getLogger(Scheduler.class);

    @Scheduled(cron = "41 17 * * * *",zone = "Indian/Maldives")
    public void sendEmailToSeller(){
        System.out.println("scheduling>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        logger.trace("running scheduler");
        emailNotificationService.sendNotification("Accepted/Rejected","order details","pallavisharma3126@gmail.com");
    }

}
