package com.gegessen.security.filters;

import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

public class CustomLogoutFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("CUSTOM LOGOUT FILTER CALLED");
        Cookie jwtCookie =  Arrays.stream(httpServletRequest.getCookies())
                .filter(cookie -> cookie.getName().equals("jwt-token"))
                .findFirst().orElse(null);

        if(jwtCookie!=null){
            jwtCookie.setValue(null);
            jwtCookie.setPath("/config/api/v1/");
            httpServletResponse.addCookie(jwtCookie);
        }

        filterChain.doFilter(httpServletRequest,httpServletResponse);
    }


    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return !request.getServletPath().equals("/config/api/v1/users/logout");
    }
}
