package com.codehustle.chatterpark.service;

import com.codehustle.chatterpark.entity.User;
import com.codehustle.chatterpark.model.UserModel;
import com.codehustle.chatterpark.repository.UserRepository;
import com.codehustle.chatterpark.security.SecurityUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final MapperService mapperService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsernameOrUserEmail(username,username);
    }

    public List<UserModel> getActiveUsers(){
        return mapperService.map(userRepository.findOnlineUsers(),UserModel.class);
    }

    @Transactional
    public void updateUserOnlineStatus(boolean isOnline,Long userId){
        userRepository.updateUserLoginStatus(isOnline, userId);
    }

    public UserModel getUser(){
        return mapperService.map(SecurityUtils.getLoggedInUser(),UserModel.class);
    }

    public User saveUser(User user){
        return userRepository.save(user);
    }
}
