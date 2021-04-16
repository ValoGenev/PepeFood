package com.gegessen.security.provider;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gegessen.dto.user.UserAllPropertiesDto;
import com.gegessen.entity.UserEntity;
import com.gegessen.model.UserType;
import com.gegessen.repository.IUserRepository;
import com.gegessen.security.authentication.FacebookAuthentication;
import com.gegessen.security.userdetails.FacebookUserDetailsServiceImpl;
import org.hibernate.collection.spi.PersistentCollection;
import org.modelmapper.Condition;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.client.RestTemplate;

import java.util.HashSet;
import java.util.Objects;

import static com.gegessen.model.UserRole.USER;
import static java.util.Objects.isNull;

public class FacebookAuthenticationProvider implements AuthenticationProvider {

    private final FacebookUserDetailsServiceImpl facebookUserDetailsService;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    public FacebookAuthenticationProvider(FacebookUserDetailsServiceImpl facebookUserDetailsService) {
        this.facebookUserDetailsService = facebookUserDetailsService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UserAllPropertiesDto user = (UserAllPropertiesDto) authentication.getPrincipal();

        String token = user.getFacebookClientId();

        System.out.println("FACEBOOK authentication provider");

        System.out.println(token);

        String appAccessToken = "440302840596636|_XGxx1aMfz0LeZV80eXxjGTfhA0"; //access_token

        String url = String.format("https://graph.facebook.com/debug_token?input_token=%s&access_token=%s", token, appAccessToken);

        RestTemplate restTemplate = new RestTemplate();

        JsonNode response;


        try {
            response = restTemplate.getForObject(url, JsonNode.class);
            System.out.println(response.toPrettyString());
            boolean isValid = response.path("data").findValue("is_valid").asBoolean();
            if (!isValid)
                throw new BadCredentialsException("Token not valid");

            String fbookUserId = response.path("data").findValue("user_id").textValue();

            response = restTemplate.getForObject(String.format("https://graph.facebook.com/%s?fields=email,name,picture&access_token=%s", fbookUserId, appAccessToken), JsonNode.class);

            JsonNode name = response.findValue("name");
            String picture = null;

            try {
                picture = response.path("picture").path("data").findValue("url").textValue();
            } catch (Exception e) {
                System.out.println("NO PICTURE");
            }


            if (!userRepository.findByFacebookClientId(fbookUserId).isPresent()) {

                UserEntity userEntity = new UserEntity();

                if (!Objects.isNull(user.getEmail()) && userRepository.findByEmail(user.getEmail()).isPresent()) {

                    UserEntity existingUser = userRepository.findByEmail(user.getEmail()).orElse(null);

                    //GETTING USER BY EMAIL
                    if (Objects.isNull(existingUser.getName())) {
                        existingUser.setName(isNull(name) ? null : name.textValue());
                    }

                    if (Objects.isNull(existingUser.getPicUrl())) {
                        existingUser.setPicUrl(isNull(picture) ? null : picture);
                    }


                    existingUser.setFacebookClientId(fbookUserId);
                    existingUser.setEmailVerificationToken(null);
                    existingUser.setValid(true);

                    userRepository.save(existingUser);

                } else {

                    userEntity.setName(isNull(name) ? null : name.textValue());
                    userEntity.setRole(USER);
                    userEntity.setValid(true);
                    userEntity.setUserType(UserType.FACEBOOK);
                    userEntity.setRestaurant(null);
                    userEntity.setOrders(new HashSet<>());
                    userEntity.setEmail(user.getEmail());
                    userEntity.setFacebookClientId(fbookUserId);
                    userEntity.setPicUrl(picture);

                    userRepository.save(userEntity);
                }

            }

            UserDetails userDetails = facebookUserDetailsService.loadUserByUsername(fbookUserId);

            return new FacebookAuthentication(userDetails.getUsername(), null, userDetails.getAuthorities());

        } catch (Exception e) {
            e.printStackTrace();
            throw new BadCredentialsException(":(");
        }

    }

    @Override
    public boolean supports(Class<?> aClass) {
        return FacebookAuthentication.class.equals(aClass);
    }


}
