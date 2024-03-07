package com.codehustle.chatterpark.controller;

import com.codehustle.chatterpark.model.LoginRequest;
import com.codehustle.chatterpark.model.UserModel;
import com.codehustle.chatterpark.service.LoginService;
import com.codehustle.chatterpark.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/user")
@RequiredArgsConstructor
public class UserController {

    private final LoginService loginService;
    private final UserService userService;

    @PostMapping(value = "/login")
    public ResponseEntity<UserModel> loginUser(@RequestBody LoginRequest loginRequest){
        return loginService.loginUser(loginRequest);
    }

    @GetMapping
    public UserModel getLoggedInUser(){
        return userService.getUser();
    }

    @GetMapping(value = "/active")
    public List<UserModel> getActiveUsers(){
        return userService.getActiveUsers();
    }

    @PostMapping(value = "/signup")
    public ResponseEntity<UserModel> signUpUser(@RequestBody LoginRequest loginRequest){
        return loginService.signUpUser(loginRequest);
    }
}
