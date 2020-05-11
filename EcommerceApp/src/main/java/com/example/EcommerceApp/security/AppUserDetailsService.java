package com.example.EcommerceApp.security;

import com.example.EcommerceApp.exception.UserNotFoundException;
import com.example.EcommerceApp.validation.EmailValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class AppUserDetailsService implements UserDetailsService {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserDao userDao;

    @Autowired
    EmailValidation emailValidation;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        boolean isValid = emailValidation.validateEmail(email);
        if (!isValid) {
            throw new RuntimeException("Email is invalid");
        }
        String encryptedPassword = passwordEncoder.encode("pass");
        System.out.println("Trying to authenticate user ::" + email);
        System.out.println("Encrypted Password ::"+encryptedPassword);
        UserDetails userDetails = userDao.loadUserByUserEmail(email);
        return userDetails;
    }
    /*@Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("Trying to authenticate user ::" + username);
        if (userDao.loadUserByUsername(username)==null)
        {
            throw new UserNotFoundException("Invalid Username entered");
        }
        else
        {
            appUser = userDao.loadUserByUsername(username);
            return appUser;
        }
    }*/
}
