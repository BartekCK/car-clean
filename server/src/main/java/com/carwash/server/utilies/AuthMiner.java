package com.carwash.server.utilies;

import com.carwash.server.models.UserPrincipal;
import org.springframework.security.core.Authentication;

public class AuthMiner {

    public static String getUsername(Authentication authentication) {
        return ((UserPrincipal) authentication.getPrincipal()).getUsername();
    }

}
