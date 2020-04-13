package com.example.EcommerceApp.Services;

import com.example.EcommerceApp.Entities.CustomerActivate;
import com.example.EcommerceApp.Entities.User;
import com.example.EcommerceApp.Events.EmailNotificationService;
import com.example.EcommerceApp.Repositories.CustomerActivateRepo;
import com.example.EcommerceApp.Repositories.UserRepository;
import com.example.EcommerceApp.Validation.EmailValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.UUID;
import javax.transaction.Transactional;
import java.util.Date;

@Service
public class CustomerActivateService {
    @Autowired
    EmailValidation email;

    @Autowired
    CustomerActivateRepo customerActivateRepo;

    @Autowired
    UserRepository userRepository;

    @Autowired
    EmailNotificationService emailNotificationService;

    @Transactional
    public String activateCustomer(String token) {
        CustomerActivate customerActivate = customerActivateRepo.findByToken(token);
        System.out.println("customer is"+customerActivate);
        try {
            if (customerActivate.getToken().equals(null)) {
                System.out.println("success");
            }
        } catch (NullPointerException ex) {
            return "invalid token";
        }
        Date date = new Date();
        long diff = date.getTime() - customerActivate.getExpiryDate().getTime();
        long diffHours = diff / (60 * 60 * 1000);
        // token expire case
        if (diffHours > 24) {
            customerActivateRepo.deleteByUserEmail(customerActivate.getUserEmail());

            String newToken = UUID.randomUUID().toString();

            CustomerActivate localCustomerActivate = new CustomerActivate();
            localCustomerActivate.setToken(newToken);
            localCustomerActivate.setUserEmail(customerActivate.getUserEmail());
            localCustomerActivate.setExpiryDate(new Date());

            customerActivateRepo.save(localCustomerActivate);

            emailNotificationService.sendNotification("RE-ACCOUNT ACTIVATE TOKEN", "http://localhost:8080/register/confirm-account?token=" + newToken, customerActivate.getUserEmail());

            return "Token has expired";
        }
        if (customerActivate.getToken().equals(token)) {
            User user = userRepository.findByEmail(customerActivate.getUserEmail());
            user.setActive(true);
            userRepository.save(user);
            emailNotificationService.sendNotification("ACCOUNT ACTIVATED", "Your account has been activated", customerActivate.getUserEmail());
            customerActivateRepo.deleteByUserEmail(customerActivate.getUserEmail());
            return "Success";
        }

        return "Success";
    }

    @Transactional
    public String resendLink(String email) {

        User user = userRepository.findByEmail(email);
        try {
            if (user.getEmail().equals(null)) {
            }
        } catch (NullPointerException ex) {
            return "no email found";
        }
        if (user.isActive()) {
            return "Account already active";
        }
        if (!user.isActive()) {
            customerActivateRepo.deleteByUserEmail(email);

            String newToken = UUID.randomUUID().toString();

            CustomerActivate localCustomerActivate = new CustomerActivate();
            localCustomerActivate.setToken(newToken);
            localCustomerActivate.setUserEmail(email);
            localCustomerActivate.setExpiryDate(new Date());

            customerActivateRepo.save(localCustomerActivate);

            emailNotificationService.sendNotification("RE-ACCOUNT ACTIVATE TOKEN", "http://localhost:8080/register/confirm-account?token" + newToken, email);

            return "Success";
        }

        return "Success";
    }
}
