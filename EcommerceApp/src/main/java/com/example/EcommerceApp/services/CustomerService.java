package com.example.EcommerceApp.services;

import com.example.EcommerceApp.dto.AddressDto;
import com.example.EcommerceApp.dto.CustomerDto;
import com.example.EcommerceApp.dto.CustomerProfileDto;
import com.example.EcommerceApp.entities.Address;
import com.example.EcommerceApp.entities.Customer;
import com.example.EcommerceApp.entities.User;
import com.example.EcommerceApp.events.EmailNotificationService;
import com.example.EcommerceApp.exception.UserNotFoundException;
import com.example.EcommerceApp.repositories.AddressRepository;
import com.example.EcommerceApp.repositories.CustomerRepository;
import com.example.EcommerceApp.repositories.UserRepository;
import com.example.EcommerceApp.validation.EmailValidation;
import com.example.EcommerceApp.validation.PasswordValidation;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class CustomerService {
  @Autowired
  private AddressRepository addressRepository;

  @Autowired
  private CustomerRepository customerRepository;

  @Autowired
    PasswordValidation passwordValidation;

  @Autowired
    EmailValidation emailValidation;

  @Autowired
    PasswordEncoder passwordEncoder;

  @Autowired
   private EmailNotificationService emailNotificationService;

  @Autowired
  private UserRepository userRepository;

    public CustomerProfileDto getCustomerDetails(Long user_id){
        Optional<Customer> customer = customerRepository.findById(user_id);
       if(customer.isPresent()){
           CustomerProfileDto customerProfileDto=new CustomerProfileDto();
           BeanUtils.copyProperties(customer.get(),customerProfileDto);
           return customerProfileDto;
       }
       else {
           throw new UserNotFoundException("User Not Found");
       }
    }
    public MappingJacksonValue getCustomerAddresses(Long user_id){
        Optional<Customer> customer=customerRepository.findById(user_id);
        if(customer.isPresent()) {
            CustomerDto customerDto = new CustomerDto();
            BeanUtils.copyProperties(customer.get(), customerDto);

            SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("addresses");
            FilterProvider filterProvider = new SimpleFilterProvider().addFilter("CustomerFilter", filter);

            MappingJacksonValue mappingJacksonValue=new MappingJacksonValue(customerDto);

            return mappingJacksonValue;
        }
        else {
            throw new UserNotFoundException("User Not Found");
        }

    }

    @Transactional
    @Modifying
    public String updateCustomer(CustomerProfileDto profileDto, Long user_id){
        Optional<Customer> customer=customerRepository.findById(user_id);
        BeanUtils.copyProperties(profileDto,customer);
        if(customer.isPresent()) {
            customer.get().setFirstName(profileDto.getFirstName());
            customer.get().setLastName(profileDto.getLastName());
            customer.get().setContact(profileDto.getContact());
            customer.get().setEmail(profileDto.getEmail());
            customerRepository.save(customer.get());
            return "Profile updated successfully";
        }
        else{
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
    public String addAddress(AddressDto addressDto,Long user_id){
        Optional<Customer> customer=customerRepository.findById(user_id);
        if(customer.isPresent()){
            Address address=new Address();
            BeanUtils.copyProperties(addressDto,address);
            addressRepository.save(address);
            return "Address added";
        }
        else {
            throw new UserNotFoundException("USer Not Found");
        }
    }
    @Transactional
    @Modifying
    public String deleteAddress(Long addressId){
        Optional<Address> address = addressRepository.findById(addressId);
        if (address.isPresent()){
            addressRepository.deleteByAddressId(addressId);
            addressRepository.save(address.get());
            return "Address deleted";
        }else {
            return "Address not found";
        }
    }

    @Transactional
    @Modifying
    public String updateAddress(AddressDto addressDto, Long addressId,Long user_id) {

        Optional<Customer> customer = customerRepository.findById(user_id);
        Address address = new Address();
        BeanUtils.copyProperties(addressDto, address);
        if (customer.isPresent()) {
            address.setAddress(addressDto.getAddress());
            address.setCity(addressDto.getCity());
            addressRepository.save(address);
            customerRepository.save(customer.get());
            return "Address updated";
        } else {
            return "Not updated";
        }

    }
}