package com.gegessen.security.userdetails;

import com.gegessen.entity.UserEntity;
import com.gegessen.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private final IUserRepository userRepository;

    public JwtUserDetailsServiceImpl(IUserRepository userRepository) {
        this.userRepository= userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        UserEntity userInDb = userRepository.findById(id).orElseThrow(()->new UsernameNotFoundException("USER NOT FOUND"));

        return new UserDetailsImpl(userInDb.getId(),null,userInDb.getRole().getGrantedAuthorities());
    }
}
