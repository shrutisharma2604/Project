package com.example.EcommerceApp.services;

import com.example.EcommerceApp.entities.User;
import com.example.EcommerceApp.repositories.UserRepository;
import com.example.EcommerceApp.security.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class ImageService {
    @Autowired
    private UserRepository userRepository;
    public User getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AppUser appUser = (AppUser) authentication.getPrincipal();
        String username = appUser.getUsername();
        return userRepository.findByEmail(username);
      /*  if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();
            return currentUserName;
        }
        return null;*/
    }
}
