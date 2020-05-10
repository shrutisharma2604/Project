package com.example.EcommerceApp.repositories;

import com.example.EcommerceApp.config.EmailConfig;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailRepository extends MongoRepository<EmailConfig,String> {
}
