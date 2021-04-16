package com.gegessen.security.userdetails;

import com.gegessen.entity.UserEntity;
import com.gegessen.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class FacebookUserDetailsServiceImpl implements UserDetailsService {

    private final IUserRepository userRepository;

    @Autowired
    public FacebookUserDetailsServiceImpl(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String facebookClientId) throws UsernameNotFoundException {
        System.out.println();

        UserEntity userInDb = userRepository.findByFacebookClientId(facebookClientId).orElseThrow(()->new UsernameNotFoundException("USER NOT FOUND"));

        return new UserDetailsImpl(userInDb.getId(),null,userInDb.getRole().getGrantedAuthorities());
    }

}
