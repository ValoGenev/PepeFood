package com.gegessen.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gegessen.dto.order.OrderWithoutRelationDto;
import com.gegessen.dto.restaurant.RestaurantWithoutRelationDto;
import com.gegessen.entity.OrderEntity;
import com.gegessen.entity.RestaurantEntity;
import com.gegessen.model.UserRole;
import com.gegessen.model.UserType;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import java.util.HashSet;
import java.util.Set;

public class UserAllPropertiesDto {


    private String id;

    private String name;

    private String email;

    private String picUrl;

    private String googleClientId;

    private String facebookClientId;

    private UserType userType;

    private UserRole role;

    private String emailVerificationToken;

    private boolean isValid;

    private RestaurantWithoutRelationDto restaurant;

    private Set<OrderWithoutRelationDto> orders;

    public UserAllPropertiesDto(
            String id,

            RestaurantWithoutRelationDto restaurant,
            Set<OrderWithoutRelationDto> orders
    ) {
        this.id = id;

        this.restaurant = restaurant;
        this.orders = orders;
    }

    public UserAllPropertiesDto() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public RestaurantWithoutRelationDto getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(RestaurantWithoutRelationDto restaurant) {
        this.restaurant = restaurant;
    }

    public Set<OrderWithoutRelationDto> getOrders() {
        return orders;
    }

    public void setOrders(Set<OrderWithoutRelationDto> orders) {
        this.orders = orders;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public String getEmailVerificationToken() {
        return emailVerificationToken;
    }

    public void setEmailVerificationToken(String emailVerificationToken) {
        this.emailVerificationToken = emailVerificationToken;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }
}
