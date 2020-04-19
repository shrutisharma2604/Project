package com.example.EcommerceApp.services;

import com.example.EcommerceApp.config.EmailNotificationService;
import com.example.EcommerceApp.dto.AddressDTO;
import com.example.EcommerceApp.dto.CustomerDTO;
import com.example.EcommerceApp.dto.CustomerProfileDTO;
import com.example.EcommerceApp.entities.Address;
import com.example.EcommerceApp.entities.Customer;
import com.example.EcommerceApp.entities.User;
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

import javax.servlet.http.HttpServletRequest;
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

    public CustomerProfileDTO getCustomerDetails(Long id){
        Optional<Customer> customer = customerRepository.findById(id);
       if(customer.isPresent()){
           CustomerProfileDTO customerProfileDto=new CustomerProfileDTO();
           BeanUtils.copyProperties(customer.get(),customerProfileDto);
           return customerProfileDto;
       }
       else {
           throw new UserNotFoundException("User Not Found");
       }
    }
    public MappingJacksonValue getCustomerAddresses(Long id){
        Optional<Customer> customer=customerRepository.findById(id);
        if(customer.isPresent()) {
            CustomerDTO customerDto = new CustomerDTO();
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
    public String updateCustomer(CustomerProfileDTO profileDto, Long id){
        Optional<Customer> customer=customerRepository.findById(id);
        BeanUtils.copyProperties(profileDto,customer);
        if(customer.isPresent()) {
            customer.get().setFirstName(profileDto.getFirstName());
            customer.get().setLastName(profileDto.getLastName());
            customer.get().setContact(profileDto.getContact());
            customer.get().setEmail(profileDto.getEmail());
            customer.get().setImage(profileDto.getImage());
            customerRepository.save(customer.get());
            return "Profile updated successfully";
        }
        else{
            throw new UserNotFoundException("User not found");
        }

    }
    @Transactional
    @Modifying
    public String updatePassword(Long id, String oldPass, String newPass, String confirmPass, HttpServletResponse httpServletResponse) {
        Optional<User> user = userRepository.findById(id);

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
    public String addAddress(AddressDTO addressDto, Long id){
        Optional<Customer> customer=customerRepository.findById(id);
        if(customer.isPresent()){
            Address address=new Address();
            address.setUser(customer.get());
            BeanUtils.copyProperties(addressDto,address);
            addressRepository.save(address);
            return "Address added";
        }
        else {
            throw new UserNotFoundException("USer Not Found");
        }
    }
    @Transactional
    public String deleteAddress(Long id, HttpServletRequest request) {
        Optional<Address> address = addressRepository.findById(id);
        if (!address.isPresent()) {
            throw  new UserNotFoundException("no address fount with id " + id);
        }
        addressRepository.deleteById(id);
        return "Address Deleted";
    }

    @Transactional
    @Modifying
    public String updateAddress(AddressDTO addressDto, Long id, Long userId){

        Optional<Customer> customer = customerRepository.findById(userId);

        if(customer.isPresent()) {
            Optional<Address> address = addressRepository.findById(id);

            if (address.isPresent()) {
                Address address1 = new Address();
                BeanUtils.copyProperties(addressDto, address1);
                address1.setDeleted(true);

                addressRepository.save(address1);

                return "Address updated";
            } else {
                return "Address not found";
            }
        }else {
            throw new UserNotFoundException("User not found");
        }

    }
}