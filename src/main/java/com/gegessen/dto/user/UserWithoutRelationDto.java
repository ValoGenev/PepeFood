package com.gegessen.dto.user;

import com.gegessen.dto.order.OrderWithoutRelationDto;
import com.gegessen.dto.restaurant.RestaurantWithoutRelationDto;
import com.gegessen.model.UserType;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Set;

public class UserWithoutRelationDto {

    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "picture_url")
    private String picUrl;

    @Column(name = "google_client_id")
    private String googleClientId;

    @Column(name = "facebook_client_id")
    private String facebookClientId;

    @Column(name = "user_type")
    @Enumerated(EnumType.STRING)
    private UserType userType;


    public UserWithoutRelationDto(
            String id) {
        this.id = id;
    }

    public UserWithoutRelationDto() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getGoogleClientId() {
        return googleClientId;
    }

    public void setGoogleClientId(String googleClientId) {
        this.googleClientId = googleClientId;
    }

    public String getFacebookClientId() {
        return facebookClientId;
    }

    public void setFacebookClientId(String facebookClientId) {
        this.facebookClientId = facebookClientId;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }
}
