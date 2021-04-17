package com.gegessen.service.email;

import com.gegessen.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.UUID;

import static com.gegessen.util.Constants.VERIFY_URL;


public class CustomMailSender {


    private final JavaMailSender mailSender;

    @Autowired
    public CustomMailSender(JavaMailSender mailSender) {
        this.mailSender=mailSender;
    }

    public void sendVerificationEmail(UserEntity user){

        String subject = "Please verify your registration";
        String senderName = "PepeFood team";
        String mailContent = "<p>Dear " + "user" + ",<p>";
        mailContent+="<p>You can sign in to PepeFood by clicking the button below. Happy eating!";


        mailContent+="<h3><a href=\"" + "pepefoodbg@gmail.com"+user.getEmailVerificationToken() + "\">VERIFY</a></h3>";


        mailContent+="<p>Thank you<br>The PepeFood Team</p>";


        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        try {
            helper.setFrom("pepefoodbg@gmail.com",senderName);
            helper.setTo(user.getEmail());
            helper.setSubject(subject);
            helper.setText(mailContent,true);

            mailSender.send(message);


        } catch (MessagingException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }


    private String generateEmailVerificationTokenUrl(String token){
        return VERIFY_URL+token;
    }



}
