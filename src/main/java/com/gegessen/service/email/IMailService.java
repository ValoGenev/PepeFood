package com.gegessen.service.email;


import com.gegessen.entity.UserEntity;

public interface IMailService {
    void sentVerificationEmail(UserEntity user);

}
