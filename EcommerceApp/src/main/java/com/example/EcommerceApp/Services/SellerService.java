package com.example.EcommerceApp.Services;

import com.example.EcommerceApp.Dto.SellerDto;
import com.example.EcommerceApp.Entities.Seller;
import com.example.EcommerceApp.Entities.User;
import com.example.EcommerceApp.Repositories.SellerRepository;
import com.example.EcommerceApp.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SellerService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SellerRepository sellerRepository;

    public String validateSeller(SellerDto sellerDto){
        StringBuilder sb = new StringBuilder();
        System.out.println("Seller Dto is : "+sellerDto);
        User user = userRepository.findByEmail(sellerDto.getEmail());

        System.out.println("Seller is : "+user);

        System.out.println("Gst number is : "+sellerDto.getGST());
        Seller seller = sellerRepository.findByGst(sellerDto.getGST());
        if (null!=user){
            sb.append("Email already exist");
        }else if(!sellerDto.getPassword().equals(sellerDto.getConfirmPassword())){
            sb.append("Passwords not matched");
        }else if(null!=seller){
            System.out.println("Seller Gst : "+seller);
            sb.append("Gst number already exist");
        }else {
            sb.append("validated");
        }
        return sb.toString();
    }
}
