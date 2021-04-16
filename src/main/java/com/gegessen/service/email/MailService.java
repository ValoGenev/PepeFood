package com.gegessen.service.email;

import com.gegessen.entity.UserEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


@Service
public class MailService implements IMailService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MailService.class);
    private final CustomMailSender mailSender;

    @Autowired
    public MailService(CustomMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sentVerificationEmail(UserEntity user) {

        mailSender.sendVerificationEmail( user);
    }


}
