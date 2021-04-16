package com.gegessen.security.authentication;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class GoogleAuthentication extends UsernamePasswordAuthenticationToken {

    public GoogleAuthentication(Object principal, Object credentials) {
        super(principal, credentials);
    }

    public GoogleAuthentication(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }
}
