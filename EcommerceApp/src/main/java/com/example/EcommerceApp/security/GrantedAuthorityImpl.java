package com.example.EcommerceApp.security;

import com.example.EcommerceApp.entities.Role;
import org.springframework.security.core.GrantedAuthority;

import java.util.Set;

public class GrantedAuthorityImpl implements GrantedAuthority {
    private String authority;

    public GrantedAuthorityImpl(String authority) {
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return authority;
    }

    @Override
    public String toString() {
        return "GrantedAuthorityImpl{" +
                "authority='" + authority + '\'' +
                '}';
    }
}