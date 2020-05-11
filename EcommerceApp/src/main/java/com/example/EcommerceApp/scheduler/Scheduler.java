package com.example.EcommerceApp.scheduler;

import com.example.EcommerceApp.services.EmailService;
import com.example.EcommerceApp.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@EnableAsync(proxyTargetClass = true)
@Component
public class Scheduler {
    @Autowired
    private ProductService productService;

    @Autowired
    private EmailService emailService;

    @Scheduled(cron = "31 15 * * * *",zone = "Indian/Maldives")
    public void run(){
        emailService.saveEmailReport();
        productService.autoUpdateVariationQuantity();
    }

}
