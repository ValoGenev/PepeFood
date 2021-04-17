package com.gegessen.security.filters;

import com.gegessen.security.authentication.JwtAuthentication;
import com.gegessen.security.jwt.JwtTokenUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;


import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

public class JwtTokenAuthenticationFilter extends OncePerRequestFilter {

    private AuthenticationManager authenticationManager;

    private final JwtTokenUtil jwtTokenUtil;

    @Autowired
    public JwtTokenAuthenticationFilter(AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

        System.out.println("JWT AUTHENTICATION FILTER");

        Cookie[] cookies = httpServletRequest.getCookies();

        Cookie jwtCookie;

        try {
            jwtCookie= Arrays.stream(cookies)
                    .filter(cookie-> cookie.getName().equals("jwt-token"))
                    .findFirst().orElseThrow(() -> new BadCredentialsException("BAD CREDENTIALS"));
        }catch (NullPointerException e){
            throw new BadCredentialsException("BAD CREDENTIALS");
        }

        if(StringUtils.isBlank(jwtCookie.getValue())) {
            throw new BadCredentialsException("BAD CREDENTIALS");
        }

        System.out.println(jwtCookie.getValue());

        Authentication auth = authenticationManager.authenticate(new JwtAuthentication(jwtCookie.getValue(), null));

        if (auth.isAuthenticated()) {

            SecurityContextHolder.getContext().setAuthentication(auth);
        }else {
            throw new BadCredentialsException("bad credentials");
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {

        String method = request.getMethod();

//        if(method.equals("DELETE") && request.getServletPath().matches("/config/api/v1/users.*") ||
//           method.equals("PUT") && request.getServletPath().matches("/config/api/v1/users.*") ||
//           method.equals("POST") && request.getServletPath().matches("/config/api/v1/users") ||
//           method.equals("POST") && request.getServletPath().matches("/config/api/v1/restaurants") ||
//           method.equals("DELETE") && request.getServletPath().matches("/config/api/v1/restaurants.*") ||
//           method.equals("PUT") && request.getServletPath().matches("/config/api/v1/restaurants.*") ||
//           method.equals("POST") && request.getServletPath().matches("/config/api/v1/products") ||
//           method.equals("DELETE") && request.getServletPath().matches("/config/api/v1/products.*") ||
//           method.equals("PUT") && request.getServletPath().matches("/config/api/v1/products.*")){
//            return false;
//        }



        return true;
    }
}
