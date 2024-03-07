package com.codehustle.chatterpark.security;

import com.codehustle.chatterpark.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.security.Principal;

public class SecurityUtils {

    public static User getLoggedInUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null){
            return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        }
        return null;
    }

    public static User getUserFromPrincipal(Principal principal){
        if(principal != null){
            return (User)((Authentication) principal).getPrincipal();
        }
        return null;
    }
}
