package com.gegessen.security.provider;

import com.gegessen.security.authentication.JwtAuthentication;
import com.gegessen.security.jwt.JwtTokenUtil;
import com.gegessen.security.userdetails.JwtUserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

public class JwtAuthenticationProvider implements AuthenticationProvider {

    private final JwtUserDetailsServiceImpl jwtUserDetailsService;

    private final JwtTokenUtil jwtTokenUtil;

    @Autowired
    public JwtAuthenticationProvider(JwtUserDetailsServiceImpl jwtUserDetailsService,
                                JwtTokenUtil jwtTokenUtil) {
        this.jwtUserDetailsService = jwtUserDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String token = (String) authentication.getPrincipal();
        String id = jwtTokenUtil.getIdFromToken(token);

        UserDetails loggedInUser = jwtUserDetailsService.loadUserByUsername(id);

        if(jwtTokenUtil.validateToken(token,loggedInUser)){
            return new JwtAuthentication(loggedInUser.getUsername(),null,loggedInUser.getAuthorities());
        }

        throw  new BadCredentialsException("BAD CREDENTIALS");
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return JwtAuthentication.class.equals(aClass);
    }
}
