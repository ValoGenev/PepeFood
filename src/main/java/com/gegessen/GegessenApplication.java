package com.gegessen;


import com.fasterxml.jackson.databind.JsonNode;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriBuilder;

import java.io.IOException;
import java.net.URI;
import java.security.GeneralSecurityException;
import java.util.Collections;

@SpringBootApplication
public class GegessenApplication {


    public static void main(String[] args) throws IOException, GeneralSecurityException {
        SpringApplication.run(GegessenApplication.class, args);

//         podavashe client access token
//         IMETO
//         EVENTUALNO EMAIL
//         SNIMKA
//
//
//        https://graph.facebook.com/552109636184770?access_token=440302840596636|_XGxx1aMfz0LeZV80eXxjGTfhA0
//        - get user info
//
//        podava se client acces token
//        String appId = "440302840596636";
//        String appSecret = "1bcd729d8dd313ba0158ed75677ec12f";
//        String appAccessToken = "440302840596636|_XGxx1aMfz0LeZV80eXxjGTfhA0"; //access_token
//
//        String clientAccessToken = "EAAGQcZC5oSJwBAB9BCOiBL5Y8g210WaH2PNiS8rbm7nTzDuyRzLpVXsNjjI7aHiBapOwZB2n6RjNuWtnfCo0vNZAaaqthV4hqwxuvFZAvakaZC1bvFZCLpE8qlZCz7XsaXeo6tPJxfOYrNO8CEEgf9xLPlQZC5Dgjy2VtqELZBmS4ZALKKZCJkmQ8ZBzLxMdxJmBhkEvXKWjfOYeAAZDZD"; //input_token
//
//        String url = String.format("https://graph.facebook.com/debug_token?input_token=%s&access_token=%s",clientAccessToken,appAccessToken);
//
//        RestTemplate restTemplate = new RestTemplate();
//
//        JsonNode response;
//
//        try {
//            response = restTemplate.getForObject(url,JsonNode.class);
//            System.out.println(response.toPrettyString());
//        }catch (Exception e){
//            throw new RuntimeException("da");
//        }
//
//        boolean isValid = response.path("data").findValue("is_valid").asBoolean();
////        if (!isValid)
////            throw new BadCredentialsException("Token not valid");
////
////        String fbookUserId = response.path("data").findValue("user_id").textValue();
////        if (fbookUserId==null){
////            throw new AuthenticationServiceException("Unable to read user_id from facebook debug_token response");
////        }
////        System.out.println(fbookUserId);
//        client id-to e tva fbookUserId
//
//
//        podava se body : {s id_token-a - TYPE GOOGLE,    {client-access-token, type }
//        v body-to shte podava samo email:
//
//        NetHttpTransport transport = new NetHttpTransport();
//        JsonFactory jsonFactory = new GsonFactory();
//
//
//        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
//                .setAudience(Collections.singletonList("948513652424-r3958b11danm82jk8dicai3iv6oe4cle.apps.googleusercontent.com"))
//                .build();
//
//        GoogleIdToken idToken = GoogleIdToken.parse(verifier.getJsonFactory(), "eyJhbGciOiJSUzI1NiIsImtpZCI6Ijc3NDU3MzIxOGM2ZjZhMmZlNTBlMjlhY2JjNjg2NDMyODYzZmM5YzMiLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJhY2NvdW50cy5nb29nbGUuY29tIiwiYXpwIjoiOTQ4NTEzNjUyNDI0LXIzOTU4YjExZGFubTgyams4ZGljYWkzaXY2b2U0Y2xlLmFwcHMuZ29vZ2xldXNlcmNvbnRlbnQuY29tIiwiYXVkIjoiOTQ4NTEzNjUyNDI0LXIzOTU4YjExZGFubTgyams4ZGljYWkzaXY2b2U0Y2xlLmFwcHMuZ29vZ2xldXNlcmNvbnRlbnQuY29tIiwic3ViIjoiMTA3ODQxNDY4OTA5MjM4NjgwNDk3IiwiZW1haWwiOiJseXViZW4udGVuZWtlZGppZXZAZ21haWwuY29tIiwiZW1haWxfdmVyaWZpZWQiOnRydWUsImF0X2hhc2giOiJhZ1Bwbl9vVUtwR3NzdDl1WnhudnN3IiwibmFtZSI6Imx5dWJlbiB0ZW5la2VkamlldiIsInBpY3R1cmUiOiJodHRwczovL2xoMy5nb29nbGV1c2VyY29udGVudC5jb20vLTRIMVZYeGdsZDJRL0FBQUFBQUFBQUFJL0FBQUFBQUFBQUFBL0FNWnV1Y2swcTNUT2VpRDdsV2w4R1UwckpadEdQbTFfQkEvczk2LWMvcGhvdG8uanBnIiwiZ2l2ZW5fbmFtZSI6Imx5dWJlbiIsImZhbWlseV9uYW1lIjoidGVuZWtlZGppZXYiLCJsb2NhbGUiOiJlbi1HQiIsImlhdCI6MTYxODMwNzczOSwiZXhwIjoxNjE4MzExMzM5LCJqdGkiOiI3MTc4YzYzODRmZGViM2VhODZlMmMwMjdlM2NjYjE3MTE1MjY4ZDYzIn0.R6aAk3uNnkfFanGuqAf4Ggu9_e714Fao2gykyfb4mJDaYg0u6wjHVT2Tp0f7skfOhmzR6MCTg_YjXpanx2wwwiPsVf6XF2rk5kYpZhRnI7APEqc6JpGIpSl0zV4yv_2h34M9hmppsUTfP2xPTTPbvO5Q6QnYWflNVaVDuMO-WnSM8r7j1dK9hzLkhad2uer2Ce2RdSegkF0thTqtOaWLwppZpnEoxJRNtw78EHVpldD2-hvcmRARaqg0oOa7E7hMkC7NLLdqGNqAi_1H-zXy7ms4sXt8n9aJ5VDEmVbqrtG7KukIWsMTfhBEnhAYOI0zGdqwpaULOHSVcpZ3Qcd6WQ");
//        boolean tokenIsValid = (idToken != null) && verifier.verify(idToken);
//
//
//        System.out.println(tokenIsValid);
//
//        System.out.println(idToken.getPayload().toPrettyString());


    }

}
