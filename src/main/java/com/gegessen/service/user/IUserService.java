package com.gegessen.service.user;

import com.gegessen.dto.user.UserAllPropertiesDto;

import java.util.Set;


public interface IUserService {

    Set<UserAllPropertiesDto> findAll();

    UserAllPropertiesDto findOne(String id);

    void delete(String id);

    UserAllPropertiesDto create(UserAllPropertiesDto user);

    UserAllPropertiesDto update(UserAllPropertiesDto user, String id);

    void sendVerificationEmail(String email);


}
