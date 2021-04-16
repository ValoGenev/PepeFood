package com.gegessen.security.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.gegessen.dto.user.UserAllPropertiesDto;
import com.gegessen.security.authentication.EmailAuthentication;
import com.gegessen.security.authentication.FacebookAuthentication;
import com.gegessen.security.authentication.GoogleAuthentication;
import com.gegessen.security.jwt.JwtTokenUtil;
import com.gegessen.security.userdetails.UserDetailsImpl;
import com.gegessen.security.wrappers.WrappedHttpServletRequest;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class SocialAuthenticationFilter extends OncePerRequestFilter {

    private final AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    public SocialAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

        System.out.println("SOCIAL AUTHENTICATION FILTER CALLED");

        httpServletRequest = new WrappedHttpServletRequest(httpServletRequest);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        UserAllPropertiesDto user = objectMapper.readValue(IOUtils.toString(httpServletRequest.getInputStream()), UserAllPropertiesDto.class);

        System.out.println(user.getUserType());

        switch (user.getUserType()) {
            case GOOGLE: {

                String googleToken = httpServletRequest.getHeader("google-login-token");

                user.setGoogleClientId(googleToken);

                Authentication a = new GoogleAuthentication(user, null);

                Authentication auth = authenticationManager.authenticate(a);

                if (auth.isAuthenticated()) {

                    System.out.println("AUTHENTICATED");

                    SecurityContextHolder.getContext().setAuthentication(auth);

                    Set<GrantedAuthority> loggedUserAuthorities= new HashSet<>(auth.getAuthorities());

                    String token = jwtTokenUtil.generateToken(new UserDetailsImpl((String) auth.getPrincipal(),null,loggedUserAuthorities));

                    Cookie cookie = new Cookie("jwt-token",token);
                    cookie.setPath("/config/api/v1/");

                    httpServletResponse.addCookie(cookie);


                }else {
                    throw new BadCredentialsException(":(");
                }

                break;
            }


            case EMAIL: {

                    String emailToken = httpServletRequest.getHeader("email-login-token");

                    user.setEmailVerificationToken(emailToken);

                    Authentication a = new EmailAuthentication(user,null);

                    Authentication auth = authenticationManager.authenticate(a);

                    if (auth.isAuthenticated()) {
                        System.out.println("AUTHENTICATED");

                        SecurityContextHolder.getContext().setAuthentication(auth);

                        Set<GrantedAuthority> loggedUserAuthorities= new HashSet<>(auth.getAuthorities());

                        String token = jwtTokenUtil.generateToken(new UserDetailsImpl((String) auth.getPrincipal(),null,loggedUserAuthorities));

                        Cookie cookie = new Cookie("jwt-token",token);
                        cookie.setPath("/config/api/v1/");

                        httpServletResponse.addCookie(cookie);

                    }else {
                        throw new BadCredentialsException(":(");
                    }



                break;
            }


            case FACEBOOK: {
                String facebookToken = httpServletRequest.getHeader("facebook-login-token");

                user.setFacebookClientId(facebookToken);

                Authentication a = new FacebookAuthentication(user, null);

                Authentication auth = authenticationManager.authenticate(a);

                if (auth.isAuthenticated()) {
                    System.out.println("AUTHENTICATED");
                    SecurityContextHolder.getContext().setAuthentication(auth);

                    Set<GrantedAuthority> loggedUserAuthorities= new HashSet<>(auth.getAuthorities());

                    String token = jwtTokenUtil.generateToken(new UserDetailsImpl((String) auth.getPrincipal(),null,loggedUserAuthorities));

                    Cookie cookie = new Cookie("jwt-token",token);
                    cookie.setPath("/config/api/v1/");

                    httpServletResponse.addCookie(cookie);

                }else {
                    throw new BadCredentialsException(":(");
                }

                break;
            }

            default:
                throw new BadCredentialsException(":(");


        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return !request.getServletPath().equals("/config/api/v1/users/login");
    }
}
