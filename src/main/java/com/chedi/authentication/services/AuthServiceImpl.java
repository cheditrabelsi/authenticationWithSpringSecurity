package com.chedi.authentication.services;

import com.chedi.authentication.dtos.SignupRequest;
import com.chedi.authentication.dtos.UserDto;
import com.chedi.authentication.entities.User;
import com.chedi.authentication.enums.UserRole;
import com.chedi.authentication.repositories.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService{
    private UserRepository userRepository;
    @PostConstruct
    public void createAdminAccount(){
        User adminAccount=this.userRepository.findByRole(UserRole.Admin);
        if(adminAccount==null){
            User newadminAccount=new User();
            newadminAccount.setEmail("admin@gmail.com");
            newadminAccount.setPassword(new BCryptPasswordEncoder().encode("admin"));
            newadminAccount.setRole(UserRole.Admin);
            newadminAccount.setNom("admin");
            this.userRepository.save(newadminAccount);

        }
    }
@Autowired
    public AuthServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDto createCustomer(SignupRequest c) {
        User user=new User();
        user.setEmail(c.getEmail());
        user.setNom(c.getName());
        user.setPassword(new BCryptPasswordEncoder().encode(c.getPassword()));
        user.setRole(UserRole.Client);
        User createCustomer=userRepository.save(user);
        UserDto userdto=new UserDto();
        userdto.setEmail(userdto.getEmail());
        userdto.setNom(userdto.getNom());
        userdto.setPassword(userdto.getPassword());
        userdto.setRole(createCustomer.getRole());
        return userdto;
    }

    @Override
    public boolean hasCustomerWithEmail(String email) {
        return userRepository.findFirstByEmail(email).isPresent();
    }
}
