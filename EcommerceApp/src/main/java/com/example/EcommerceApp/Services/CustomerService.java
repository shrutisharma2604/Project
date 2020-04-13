package com.example.EcommerceApp.Services;

import com.example.EcommerceApp.Dto.CustomerDto;
import com.example.EcommerceApp.Entities.Customer;
import com.example.EcommerceApp.Entities.User;
import com.example.EcommerceApp.Repositories.CustomerRepository;
import com.example.EcommerceApp.Repositories.UserRepository;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
  @Autowired
  private UserRepository userRepository;

  @Autowired
  private CustomerRepository customerRepository;
    public String validateCustomer(CustomerDto customerDto){
        StringBuilder sb = new StringBuilder();
        User user = userRepository.findByEmail(customerDto.getEmail());
        if (null!=user){
            sb.append("Email already exist");
        }else if(!customerDto.getPassword().equals(customerDto.getConfirmPassword())){
            sb.append("Password not matched");
        }else {
            sb.append("validated");
        }
        return sb.toString();
    }
    public MappingJacksonValue getCustomerDetails(String email){
        Customer customer = customerRepository.findByEmail(email);
        CustomerDto customerDto = new CustomerDto();
        BeanUtils.copyProperties(customer,customerDto);

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.serializeAllExcept("password","confirmPassword","accountNonLocked","roles");
        FilterProvider filterProvider = new SimpleFilterProvider().addFilter("CustomerDto-Filter",filter);

        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(customerDto);
        mappingJacksonValue.setFilters(filterProvider);

        return mappingJacksonValue;
    }

}
