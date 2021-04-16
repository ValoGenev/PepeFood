package com.gegessen.security.provider;

import com.gegessen.dto.user.UserAllPropertiesDto;
import com.gegessen.entity.UserEntity;
import com.gegessen.model.UserRole;
import com.gegessen.model.UserType;
import com.gegessen.repository.IUserRepository;
import com.gegessen.security.authentication.GoogleAuthentication;
import com.gegessen.security.userdetails.GoogleUserDetailsServiceImpl;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;

import static com.gegessen.model.UserRole.USER;
import static java.util.Objects.isNull;

public class GoogleAuthenticationProvider implements AuthenticationProvider {

    private final GoogleUserDetailsServiceImpl userDetailsService;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    public GoogleAuthenticationProvider(GoogleUserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        UserAllPropertiesDto user = (UserAllPropertiesDto) authentication.getPrincipal();

        String token = user.getGoogleClientId();

        System.out.println("GOOGLE authentication provider");

        System.out.println(token);

        NetHttpTransport transport = new NetHttpTransport();
        JsonFactory jsonFactory = new GsonFactory();


        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
                .setAudience(Collections.singletonList("948513652424-r3958b11danm82jk8dicai3iv6oe4cle.apps.googleusercontent.com"))
                .build();


        try {
            GoogleIdToken idToken = GoogleIdToken.parse(verifier.getJsonFactory(), token);
            boolean tokenIsValid = (idToken != null) && verifier.verify(idToken);

            if (tokenIsValid) {
                System.out.println(idToken.getPayload().toPrettyString());

                String email = idToken.getPayload().getEmail();
                String name = (String) idToken.getPayload().get("name");
                String picUrl = (String) idToken.getPayload().get("picture");
                String googleId = (String) idToken.getPayload().get("sub");


                if (!userRepository.findByGoogleClientId(googleId).isPresent()) {

                    UserEntity userEntity = new UserEntity();

                    if (!Objects.isNull(email) && userRepository.findByEmail(email).isPresent()) {

                        UserEntity existingUser = userRepository.findByEmail(email).orElse(null);

                        //GETTING USER BY EMAIL
                        if (Objects.isNull(existingUser.getName())) {
                            existingUser.setName(isNull(name) ? null : name);
                        }

                        if (Objects.isNull(existingUser.getPicUrl())) {
                            existingUser.setPicUrl(isNull(picUrl) ? null : picUrl);
                        }

                        existingUser.setGoogleClientId(googleId);
                        existingUser.setEmailVerificationToken(null);
                        existingUser.setValid(true);

                        userRepository.save(existingUser);

                    } else {
                        userEntity.setRestaurant(null);
                        userEntity.setOrders(new HashSet<>());
                        userEntity.setEmail(email);
                        userEntity.setPicUrl(picUrl);
                        userEntity.setName(name);
                        userEntity.setGoogleClientId(googleId);
                        userEntity.setRole(UserRole.USER);
                        userEntity.setUserType(UserType.GOOGLE);
                        userEntity.setValid(true);

                        userRepository.save(userEntity);
                    }

                }

                UserDetails userDetails = userDetailsService.loadUserByUsername(googleId);

                return new GoogleAuthentication(userDetails.getUsername(), null, userDetails.getAuthorities());
            }
        } catch (Exception e) {
            System.out.println("INVALID GOOGLE TOKEN");
            throw new BadCredentialsException(":(");
        }


        return null;

    }

    @Override
    public boolean supports(Class<?> aClass) {
        return GoogleAuthentication.class.equals(aClass);
    }
}
