package com.example.EcommerceApp.services;

import com.example.EcommerceApp.dto.*;
import com.example.EcommerceApp.entities.Address;
import com.example.EcommerceApp.entities.Customer;
import com.example.EcommerceApp.entities.Seller;
import com.example.EcommerceApp.entities.User;
import com.example.EcommerceApp.events.EmailNotificationService;
import com.example.EcommerceApp.exception.UserNotFoundException;
import com.example.EcommerceApp.repositories.AddressRepository;
import com.example.EcommerceApp.repositories.SellerRepository;
import com.example.EcommerceApp.repositories.UserRepository;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class SellerService {
    @Autowired
    private SellerRepository sellerRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailNotificationService emailNotificationService;

    @Autowired
    PasswordEncoder passwordEncoder;

    public SellerProfileDto getSellerDetails(Long user_id){
        Optional<Seller> seller = sellerRepository.findById(user_id);

        if(seller.isPresent()){
            SellerProfileDto sellerProfileDto=new SellerProfileDto();
            BeanUtils.copyProperties(seller.get(),sellerProfileDto);
            return sellerProfileDto;
        }
        else {
            throw new UserNotFoundException("User Not Found");
        }
    }
    @Transactional
    @Modifying
    public String updateSeller(SellerProfileDto sellerProfileDto, Long userId){
        Optional<Seller> seller = sellerRepository.findById(userId);
        if(seller.isPresent()) {
            seller.get().setFirstName(sellerProfileDto.getFirstName());
            seller.get().setLastName(sellerProfileDto.getLastName());
            seller.get().setCompanyContact(sellerProfileDto.getCompanyContact());
            seller.get().setGST(sellerProfileDto.getGST());

            sellerRepository.save(seller.get());
            return "Profile update Successfully";
        }else {
            throw new UserNotFoundException("User not found");
        }
    }

    @Transactional
    @Modifying
    public String updatePassword(Long user_id, String oldPass, String newPass, String confirmPass, HttpServletResponse httpServletResponse) {
        Optional<User> user = userRepository.findById(user_id);

        if (user.isPresent()) {
            if (passwordEncoder.matches(oldPass, user.get().getPassword())) {

                if (newPass.equals(confirmPass)) {
                    user.get().setPassword(passwordEncoder.encode(newPass));
                    userRepository.save(user.get());

                    String email = user.get().getEmail();
                    emailNotificationService.sendNotification("Password Changed", "Your password has changed", email);

                    return "Password successfully changed";
                } else {
                    httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                }
            } else {
                httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        } else {
            throw new UserNotFoundException("User not found");
        }
        return "Success";
    }

    @Transactional
    @Modifying
    public String updateAddress(AddressDto addressDto, Long addressId,Long user_id) {

        Optional<Seller> seller = sellerRepository.findById(user_id);
        Address address = new Address();
        BeanUtils.copyProperties(addressDto, address);
        if (seller.isPresent()) {
            address.setAddress(addressDto.getAddress());
            address.setCity(addressDto.getCity());
            addressRepository.save(address);
            sellerRepository.save(seller.get());
            return "Address updated";
        } else {
            return "Not updated";
        }

    }
}
