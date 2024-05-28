package com.dkf.ODAD.auth;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class AuthHelper {

    public String getAuthenticatedUserEmail(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication!=null && authentication.isAuthenticated()){
            Object principal = authentication.getPrincipal();

            if (principal instanceof UserDetails){
                return ((UserDetails) principal).getUsername();
            } else if (principal instanceof String){
                return principal.toString();
            }
        }
        throw new RuntimeException("User not authenticated");
    }
}
