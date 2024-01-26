package com.chedi.authentication.services;

import com.chedi.authentication.dtos.SignupRequest;
import com.chedi.authentication.dtos.UserDto;

public interface AuthService {
    UserDto createCustomer(SignupRequest c);
    boolean hasCustomerWithEmail(String email);
}
