package com.gegessen.security.authentication;

import com.gegessen.security.provider.FacebookAuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class FacebookAuthentication extends UsernamePasswordAuthenticationToken {

    public FacebookAuthentication(Object principal, Object credentials) {
        super(principal, credentials);
    }

    public FacebookAuthentication(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }
}
