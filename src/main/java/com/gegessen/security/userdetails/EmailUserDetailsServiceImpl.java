package com.gegessen.security.userdetails;

import com.gegessen.entity.UserEntity;
import com.gegessen.repository.IUserRepository;
import org.apache.catalina.UserDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class EmailUserDetailsServiceImpl implements UserDetailsService {

    private final IUserRepository userRepository;

    @Autowired
    public EmailUserDetailsServiceImpl(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String verificationToken) throws UsernameNotFoundException {
        UserEntity userInDb = userRepository.findByEmailVerificationToken(verificationToken).orElseThrow(()->new UsernameNotFoundException("USER NOT FOUND"));


        if (!Objects.isNull(userInDb.getEmail())) {


        }

        userInDb.setValid(true);
        userInDb.setEmailVerificationToken(null);
        userRepository.save(userInDb);

        return new UserDetailsImpl(userInDb.getId(),null,userInDb.getRole().getGrantedAuthorities());
    }
}
