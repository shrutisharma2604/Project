package com.example.EcommerceApp.services;

import com.example.EcommerceApp.config.EmailConfig;
import com.example.EcommerceApp.config.EmailNotificationService;
import com.example.EcommerceApp.entities.Seller;
import com.example.EcommerceApp.repositories.SellerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Service;

import java.util.Iterator;

@Service
public class EmailService {
    @Autowired
    private SellerRepository sellerRepository;

    @Autowired
    private EmailNotificationService emailNotificationService;

    @Autowired
    private MongoOperations mongoOperations;

    Logger logger = LoggerFactory.getLogger(EmailService.class);

    public void saveEmailReport() {

        Iterable<Seller> sellers = sellerRepository.findAll();
        Iterator<Seller> sellerIterator = sellers.iterator();
        while (sellerIterator.hasNext()) {
            Seller seller = sellerIterator.next();
            emailNotificationService.sendNotification("Accepted/Rejected","order details",seller.getEmail());

            EmailConfig emailConfig = new EmailConfig();

            emailConfig.setEmail(seller.getEmail());
            emailConfig.setSubject("Accepted/Rejected");
            emailConfig.setMessage("order details");

            mongoOperations.save(emailConfig,"emailConfig");

            logger.info("Email sent to all sellers");

        }
    }
}
