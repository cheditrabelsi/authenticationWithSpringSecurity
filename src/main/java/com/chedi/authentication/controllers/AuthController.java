package com.chedi.authentication.controllers;

import com.chedi.authentication.dtos.AuthenticationRequest;
import com.chedi.authentication.dtos.AuthenticationResponse;
import com.chedi.authentication.dtos.SignupRequest;
import com.chedi.authentication.dtos.UserDto;
import com.chedi.authentication.entities.User;
import com.chedi.authentication.util.jwtUtil;
import com.chedi.authentication.repositories.UserRepository;
import com.chedi.authentication.services.AuthServiceImpl;
import com.chedi.authentication.services.jwt.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private AuthServiceImpl authService;
    @Autowired
    private UserService userService;
    @Autowired
    private jwtUtil JwtUtil;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthenticationManager authenticationManager;

    public AuthController(AuthServiceImpl authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> register(@RequestBody SignupRequest signup){
        if(authService.hasCustomerWithEmail(signup.getEmail())){
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Email already exist");
        }
        UserDto createUserDto=authService.createCustomer(signup);
        if(createUserDto==null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad request!");
        }else{
            return ResponseEntity.status(HttpStatus.CREATED).body("add with succes");
        }
    }
    @PostMapping("/login")
    public AuthenticationResponse createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws
            BadCredentialsException,
            DisabledException,
            UsernameNotFoundException{
        try{
authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(),authenticationRequest.getPassword()));
        }catch (BadCredentialsException e){
            logger.info(String.valueOf(e));
            throw new BadCredentialsException("incorrect username or password");

        }
        final UserDetails userDetails=userService.userDetailsService().loadUserByUsername(authenticationRequest.getEmail());
        Optional<User> optionalUser=userRepository.findFirstByEmail(userDetails.getUsername());
        final String jwt=JwtUtil.generateToken(userDetails);
        AuthenticationResponse authenticationResponse=new AuthenticationResponse();
            authenticationResponse.setJwt(jwt);
            authenticationResponse.setUserId(optionalUser.get().getId());
            authenticationResponse.setUserRole(optionalUser.get().getRole());
            logger.info(String.valueOf(optionalUser));
        return authenticationResponse;
    }
}
