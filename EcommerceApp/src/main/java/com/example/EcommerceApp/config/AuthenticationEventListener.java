package com.example.EcommerceApp.config;

import com.example.EcommerceApp.entities.User;
import com.example.EcommerceApp.entities.UserLoginAttempts;
import com.example.EcommerceApp.repositories.UserAttemptsRepo;
import com.example.EcommerceApp.repositories.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Optional;

@Component
public class AuthenticationEventListener {
    @Autowired
    UserAttemptsRepo userAttemptsRepo;

    @Autowired
    UserRepository userRepository;

    @Autowired
    EmailNotificationService emailNotificationService;

        private static final Logger LOGGER = LogManager.getLogger(AuthenticationEventListener.class);



    @EventListener
    public void authenticationPass(AuthenticationSuccessEvent event) {
        String username = "";
        LinkedHashMap<String, String> userMap = new LinkedHashMap<>();

        try {
            userMap = (LinkedHashMap<String, String>) event.getAuthentication().getPrincipal();
        } catch (ClassCastException ex) {
            LOGGER.error("ClassCastException --{}", ex);
        }

        try {
            username = userMap.get("username");
        } catch (NullPointerException e) {
            LOGGER.error("NullPointerException --{}", e);
        }
        Optional<UserLoginAttempts> userLoginFailCounter = userAttemptsRepo.findByEmail(username);
        if (userLoginFailCounter.isPresent()){
            userAttemptsRepo.deleteById(userLoginFailCounter.get().getId());
        }
    }

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

}
