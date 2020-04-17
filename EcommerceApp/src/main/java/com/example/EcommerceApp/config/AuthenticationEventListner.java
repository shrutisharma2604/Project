package com.example.EcommerceApp.config;

import com.example.EcommerceApp.entities.User;
import com.example.EcommerceApp.entities.UserLoginAttempts;
import com.example.EcommerceApp.events.EmailNotificationService;
import com.example.EcommerceApp.repositories.UserAttemptsRepo;
import com.example.EcommerceApp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Optional;

@Component
public class AuthenticationEventListner {
    @Autowired
    UserAttemptsRepo userAttemptsRepo;

    @Autowired
    UserRepository userRepository;

    @Autowired
    EmailNotificationService emailNotificationService;

    @EventListener
    public void authenticationFailed(AuthenticationFailureBadCredentialsEvent event) {
        int counter;
        String userEmail = (String) event.getAuthentication().getPrincipal();
        Optional<UserLoginAttempts> userLoginFailCounter = userAttemptsRepo.findByEmail(userEmail);

        if (!userLoginFailCounter.isPresent()) {
            UserLoginAttempts userLoginFailCounter1 = new UserLoginAttempts();
            userLoginFailCounter1.setAttempts(1);
            userLoginFailCounter1.setEmail(userEmail);
            userAttemptsRepo.save(userLoginFailCounter1);
        }
        if (userLoginFailCounter.isPresent()) {
            counter = userLoginFailCounter.get().getAttempts();
            System.out.println(counter);
            if (counter>=2) {
                User user = userRepository.findByEmail(userEmail);
                user.setLocked(true);
                emailNotificationService.sendNotification("ACCOUNT LOCKED","YOUR ACCOUNT HAS BEEN LOCKED",userEmail);
                userRepository.save(user);
            }
            UserLoginAttempts userLoginFailCounter1 = userLoginFailCounter.get();
            userLoginFailCounter1.setAttempts(++counter);
            userLoginFailCounter1.setEmail(userEmail);
            System.out.println(userLoginFailCounter1+"-----------------");
            userAttemptsRepo.save(userLoginFailCounter1);
        }

    }

    @EventListener
    public void authenticationPass(AuthenticationSuccessEvent event) {
        LinkedHashMap<String,String> userMap = new LinkedHashMap<>();
        try {
            userMap = (LinkedHashMap<String, String>) event.getAuthentication().getDetails();
        } catch (ClassCastException ex) {

        }
        String userEmail = new String();
        try {
            userEmail = userMap.get("username");
        } catch (NullPointerException e) {
        }
        Optional<UserLoginAttempts> userLoginFailCounter = userAttemptsRepo.findByEmail(userEmail);
        if (userLoginFailCounter.isPresent()){
            userAttemptsRepo.deleteById(userLoginFailCounter.get().getId());
        }
    }

}
