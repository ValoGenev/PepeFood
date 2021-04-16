package com.gegessen.security.provider;

import com.gegessen.dto.user.UserAllPropertiesDto;
import com.gegessen.repository.IUserRepository;
import com.gegessen.security.authentication.EmailAuthentication;
import com.gegessen.security.userdetails.EmailUserDetailsServiceImpl;
import com.gegessen.security.userdetails.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

public class EmailAuthenticationProvider implements AuthenticationProvider {

    private final EmailUserDetailsServiceImpl emailUserDetailsService;

    @Autowired
    private IUserRepository userRepository;


    @Autowired
    public EmailAuthenticationProvider(EmailUserDetailsServiceImpl emailUserDetailsService) {
        this.emailUserDetailsService = emailUserDetailsService;
    }


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        System.out.println("EMAIL authentication provider");

        UserAllPropertiesDto user = (UserAllPropertiesDto) authentication.getPrincipal();

        System.out.println(user.getEmailVerificationToken());

        UserDetails userDetails = emailUserDetailsService.loadUserByUsername(user.getEmailVerificationToken());

        return new EmailAuthentication(userDetails.getUsername(), null, userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return EmailAuthentication.class.equals(aClass);
    }
}
