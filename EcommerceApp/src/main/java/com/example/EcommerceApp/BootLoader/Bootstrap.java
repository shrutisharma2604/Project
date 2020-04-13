package com.example.EcommerceApp.BootLoader;
import com.example.EcommerceApp.Entities.*;
import com.example.EcommerceApp.Repositories.RoleRepository;
import com.example.EcommerceApp.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class Bootstrap implements ApplicationRunner {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if(userRepository.count()<1){
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            Admin admin = new Admin();
            admin.setFirstName("Shruti");
            admin.setLastName("Sharma");
            admin.setEmail("shrutisharma260419@gmail.com");
            admin.setPassword(passwordEncoder.encode("pass"));
            admin.setActive(true);
            admin.setDeleted(false);

            Role role = new Role();
            role.setAuthority("ROLE_ADMIN");
            Role role1 = new Role();
            role1.setAuthority("ROLE_CUSTOMER");
            Role role2 = new Role();
            role1.setAuthority("ROLE_SELLER");
            Set<Role> roleSet = new HashSet<>();
            roleSet.add(role);
            roleSet.add(role1);
            roleSet.add(role2);
            admin.setRoles(roleSet);

            userRepository.save(admin);
            System.out.println("Total users saved::"+userRepository.count());
        }
    }
}