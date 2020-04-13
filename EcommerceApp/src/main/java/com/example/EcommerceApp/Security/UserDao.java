package com.example.EcommerceApp.Security;

import com.example.EcommerceApp.Entities.Seller;
import com.example.EcommerceApp.Entities.User;
import com.example.EcommerceApp.Exception.UserNotFoundException;
import com.example.EcommerceApp.Repositories.SellerRepository;
import com.example.EcommerceApp.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDao {
    @Autowired
    UserRepository userRepository;

    public AppUser loadUserByEmail(String email) {
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
}

