package com.example.EcommerceApp.services;

import com.example.EcommerceApp.entities.ForgotPasswordToken;
import com.example.EcommerceApp.entities.User;
import com.example.EcommerceApp.config.EmailNotificationService;
import com.example.EcommerceApp.repositories.PasswordRepo;
import com.example.EcommerceApp.repositories.UserRepository;
import com.example.EcommerceApp.validation.EmailValidation;
import com.example.EcommerceApp.validation.PasswordValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.UUID;

@Service
public class ForgotPasswordService {
    @Autowired
    EmailValidation emailValidation;

    @Autowired
    UserRepository userRepository;

    @Autowired
    EmailNotificationService emailNotificationService;
    @Autowired
    PasswordValidation passwordValidation;

    @Autowired
    PasswordRepo passwordRepo;

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    /**
     * This method is used to generate forgot password token
     * @param email
     * @return
     */
    public String sendToken(String email) {
        boolean isValidEmail = emailValidation.validateEmail(email);
        if (!isValidEmail) {
            return "email is invalid";
        }
        User user = userRepository.findByEmail(email);
        try {
            if (user.getEmail().equals(null)) {
                return "success";
            }
        } catch (NullPointerException ex) {
            return "no email found";
        }

        String token = UUID.randomUUID().toString();

        ForgotPasswordToken forgotPasswordToken = new ForgotPasswordToken();
        forgotPasswordToken.setToken(token);
        forgotPasswordToken.setExpiryDate(new Date());
        forgotPasswordToken.setUserEmail(email);

        passwordRepo.save(forgotPasswordToken);

        emailNotificationService.sendNotification("FORGOT PASSWORD", token, email);

        return "Success";
    }

    /**
     * This method is used to reset the password
     * @param email
     * @param token
     * @param pass
     * @param cpass
     * @return
     */
    @Transactional
    public String resetPassword(String email, String token, String pass, String cpass) {
        if (!pass.equals(cpass)) {
            return "password and confirm password not match";
        }
        if (!passwordValidation.validatePassword(pass,cpass)) {
            return "invalid password syntax";
        }
        ForgotPasswordToken forgotPasswordToken = passwordRepo.findByUserEmail(email);
        try {
            if (forgotPasswordToken.getUserEmail().equals(null)) {
                return "success";
            }
        } catch (NullPointerException ex) {
            return "no email found";
        }
        Date date = new Date();
        long diff = date.getTime() - forgotPasswordToken.getExpiryDate().getTime();
        long diffHours = diff / (60 * 60 * 1000);
        if (diffHours > 24) {
            passwordRepo.deleteByUserEmail(email);
            return "Token has expired";
        }
        if (!forgotPasswordToken.getToken().equals(token)) {
            return "invalid token";
        }
        if (forgotPasswordToken.getToken().equals(token)) {
            User user = userRepository.findByEmail(email);
            user.setPassword(passwordEncoder.encode(pass));
            userRepository.save(user);
            passwordRepo.deleteByUserEmail(email);
            emailNotificationService.sendNotification("PASSWORD CHANGED", "YOUR PASSWORD HAS BEEN CHANGED", email);
            return "Success";
        }
        return "Password Changed Successfully";
    }
}
