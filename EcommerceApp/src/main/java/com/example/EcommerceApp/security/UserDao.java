package com.example.EcommerceApp.security;

import com.example.EcommerceApp.entities.User;
import com.example.EcommerceApp.exception.UserNotFoundException;
import com.example.EcommerceApp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class UserDao {
    @Autowired
    UserRepository userRepository;

    AppUser loadUserByUserEmail(String email) {
        User user = userRepository.findByEmail(email);
        System.out.println(user);
        if (email != null) {
            List<GrantedAuthorityImpl> authorities = new ArrayList<>();
            user.getRoles().forEach(role -> {
                GrantedAuthorityImpl grantAuthority = new GrantedAuthorityImpl(role.getAuthority());
                authorities.add(grantAuthority);
                System.out.println("authority"+authorities);
            });
            return new AppUser(user.getFirstName(), user.getPassword(), authorities,user.isActive(),!user.isLocked(),!user.isExpired());
        } else {
            throw new RuntimeException();
        }

    }
    /*public AppUser loadUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        System.out.println(user);
        if (user != null) {
            List<GrantedAuthorityImpl> authorities = new ArrayList<>();
            user.getRoles().forEach(role -> {
                GrantedAuthorityImpl grantAuthority = new GrantedAuthorityImpl(role.getAuthority());
                authorities.add(grantAuthority);
                System.out.println("authority" + authorities);
            });
            return new AppUser(user.getFirstName(), user.getPassword(), authorities, user.isActive(), !user.isLocked(), !user.isExpired());
        } else {
            throw new RuntimeException();
        }
    }*/
}

