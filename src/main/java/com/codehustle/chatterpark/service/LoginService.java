package com.codehustle.chatterpark.service;

import com.codehustle.chatterpark.entity.User;
import com.codehustle.chatterpark.model.LoginRequest;
import com.codehustle.chatterpark.model.UserModel;
import com.sun.jdi.request.DuplicateRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    private final UserService userService;
    private final MapperService mapperService;
    private final PasswordEncoder passwordEncoder;

    public ResponseEntity<UserModel> loginUser(LoginRequest loginRequest){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),loginRequest.getPassword()));
        if(authentication != null){
            SecurityContextHolder.clearContext();
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String authToken = jwtService.generateToken();
            if(authToken != null){
                return ResponseEntity.ok()
                        .header("Authorization","Bearer "+authToken)
                        .build();
            }
        }
        return null;
    }

    public ResponseEntity<UserModel> signUpUser(LoginRequest loginRequest){
        UserDetails userDetails = userService.loadUserByUsername(loginRequest.getUsername());
        if(userDetails != null)
            throw new DuplicateRequestException("User name or email exists !");
        User mappedUSer = mapperService.map(loginRequest,User.class);
        mappedUSer.setAge(mappedUSer.getDob().until(LocalDate.now()).getYears());
        mappedUSer.setPassword(passwordEncoder.encode(mappedUSer.getPassword()));
        User user = userService.saveUser(mappedUSer);

        if(user != null){
            return loginUser(loginRequest);
        }
        return null;
    }
}
