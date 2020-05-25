package com.example.EcommerceApp.controller;

import com.example.EcommerceApp.config.EmailNotificationService;
import com.example.EcommerceApp.repositories.AddressRepository;
import com.example.EcommerceApp.repositories.CustomerActivateRepo;
import com.example.EcommerceApp.repositories.SellerRepository;
import com.example.EcommerceApp.repositories.UserRepository;
import com.example.EcommerceApp.services.CustomerActivateService;
import com.example.EcommerceApp.services.RegisterService;
import com.example.EcommerceApp.validation.EmailValidation;
import com.example.EcommerceApp.validation.GstValidation;
import com.example.EcommerceApp.validation.PasswordValidation;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@ContextConfiguration(classes = {RegistrationController.class, RegisterService.class, CustomerActivateService.class, EmailNotificationService.class})
@WebMvcTest(RegistrationController.class)
class RegistrationControllerTest  {

    @Autowired
    private MockMvc mockMvc;

    @MockBean(name = "userRepository")
    private UserRepository userRepository;

    @MockBean(name = "addressRepository")
    private AddressRepository addressRepository;

    @MockBean(name = "sellerRepository")
    SellerRepository sellerRepository;

    @MockBean(name = "customerActivateRepo")
    CustomerActivateRepo customerActivateRepo;

    @MockBean(name = "passwordValidation")
    PasswordValidation passwordValidation;

    @MockBean(name = "gstValidation")
    GstValidation gstValidation;

    @MockBean(name = "emailValidation")
    EmailValidation emailValidation;

    @MockBean(name = "messageSource")
    private MessageSource messageSource;

    @MockBean(name = "javaMailSender")
    private JavaMailSenderImpl javaMailSender;

    @MockBean(name = "tokenStore")
    private TokenStore tokenStore;


    /* @BeforeEach
     public void setup() {
         MockitoAnnotations.initMocks(this);

         this.mockMvc = MockMvcBuilders.standaloneSetup(registrationController).build();

     }*/
    @Test
    public void registerCustomer() throws Exception {
        //Given
    /*    CustomerDTO customerDTO=new CustomerDTO();
        customerDTO.setFirstName("Shruti");
        customerDTO.setLastName("Sharma");
        customerDTO.setContact("9870737979");
        customerDTO.setEmail("madhurisharma242@gmail.com");
        customerDTO.setPassword("Shruti@26");
        customerDTO.setConfirmPassword("Shruti@26");*/
        //HttpServletResponse response = mock(HttpServletResponse.class);
        Mockito.when(javaMailSender.getHost()).thenReturn("qwertyuiop");
        Mockito.when(javaMailSender.getPort()).thenReturn(10000);
        Mockito.when(javaMailSender.getUsername()).thenReturn("qwertyui");
        Mockito.when(javaMailSender.getPassword()).thenReturn("qwertyui");

        List addresses = new ArrayList();

        addresses.add(new JSONObject().put("address", "Rudrapur")
                .put("city","Rudrapur")
                .put("state","Uttarakhand")
                .put("country","India")
                .put("zipCode","263153")
                .put("label","Home"));
        String jsonString = new JSONObject()
                .put("confirmPassword", "Shruti@26")
                .put("contact", "9870737979")
                .put("email", "madhurisharma242@gmail.com")
                .put("firstName", "Madhuri")
                .put("lastName", "Sharma")
                .put("password", "Shruti@26")
                .toString();
        System.out.println(jsonString);

        // when
        RequestBuilder requestBuilder =  MockMvcRequestBuilders
                .post("/register/customer" )
                .content(jsonString)
                .contentType(MediaType.APPLICATION_JSON)
                .with(SecurityMockMvcRequestPostProcessors.user("madhurisharma242@gmail.com"))
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .accept(MediaType.APPLICATION_JSON);

        ResultActions resultAction =  mockMvc.perform(requestBuilder);
        MvcResult result = resultAction
             //   .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isCreated())
                .andReturn();
        assertEquals("Registered Successfully",result.getResponse().getContentAsString());

    }

}