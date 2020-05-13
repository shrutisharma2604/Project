package com.example.EcommerceApp;

import com.example.EcommerceApp.entities.*;
import com.example.EcommerceApp.repositories.CategoryRepository;
import com.example.EcommerceApp.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.Set;

@SpringBootTest
class EcommerceAppApplicationTests {


	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	@Test
	void contextLoads() {
	}

	@Test
	void createAdmin(){
		Admin admin=new Admin();
		admin.setFirstName("Shruti");
		admin.setLastName("Sharma");
		admin.setEmail("shrutisharma260419@gmail.com");
		admin.setPassword("pass");

		Role role=new Role();
		role.setAuthority("ROLE_ADMIN");
		Set<Role> roleSet = new HashSet<>();
		roleSet.add(role);

		// ADDING ROLES IN ADMIN
		admin.setRoles(roleSet);

		// SAVE ADMIN
		userRepository.save(admin);
	}
	@Test
	void createSellerUser() {
		Seller seller = new Seller();
		seller.setFirstName("Rishab");
		seller.setLastName("Singh");
		seller.setEmail("rishabh.singh@gmail.com");
		seller.setPassword("rishabSingh123");

		// DEFINE ROLE FOR SELLER
		Role role = new Role();
		role.setAuthority("ROLE_SELLER");
		Role role1 = new Role();
		role1.setAuthority("ROLE_SELLER_TEST");
		Set<Role> roleSet = new HashSet<>();
		roleSet.add(role);
		roleSet.add(role1);

		seller.setRoles(roleSet);
		userRepository.save(seller);
	}

	@Test
	void createBuyerUser() {
		Customer customer = new Customer();
		customer.setFirstName("Rohit");
		customer.setLastName("Sharma");
		customer.setEmail("rohit.sharma@gmail.com");
		customer.setPassword("rohitSharma123");


		// DEFINING ROLE TO BUYER
		Role role = new Role();
		role.setAuthority("ROLE_BUYER");
		Set<Role> roleSet = new HashSet<>();
		roleSet.add(role);

		// ADDING ROLES IN ADMIN
		customer.setRoles(roleSet);

		// DEFINE ADDRESS FOR ROHIT (SPECIFIC TO BUYER)
		Address address = new Address();
		address.setCountry("Street 1");
		address.setCity("Mumbai");
		address.setZipCode(100190);
		address.setAddress("Take left from crossover");
		address.setState("Maharashtra");
		address.setUser(customer);


		Set<Address> addresses=new HashSet<>();
		addresses.add(address);

		customer.setAddresses(addresses);

		// SAVING ROHIT
		userRepository.save(customer);
	}

	@Test
	void addCategory(){
		Category category=new Category();
		category.setName("Fashion");
		Product product=new Product();
		product.setBrand("Samsung");
		product.setDescription("Stereo Speaker");
		product.setName("One Plus 7");
		categoryRepository.save(category);
	}
}
