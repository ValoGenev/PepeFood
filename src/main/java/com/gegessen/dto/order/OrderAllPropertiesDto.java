package com.gegessen.dto.order;

import com.gegessen.dto.user.UserWithoutRelationDto;
import com.gegessen.dto.bucket.BucketWithoutOrderRelationDto;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.time.LocalDateTime;
import java.util.Set;

public class OrderAllPropertiesDto {

    @Null(message = "id should be null")
    private String id;

    private double totalPrice;

    private LocalDateTime orderTime;

    @NotNull(message = "user cannot be null")
    private UserWithoutRelationDto owner;

    @Valid
    private Set<BucketWithoutOrderRelationDto> buckets;

    public OrderAllPropertiesDto(
            String id,
            double totalPrice,
            LocalDateTime orderTime,
            UserWithoutRelationDto owner,
            Set<BucketWithoutOrderRelationDto> buckets) {
        this.id = id;
        this.totalPrice = totalPrice;
        this.orderTime = orderTime;
        this.owner = owner;
        this.buckets = buckets;
    }

    public OrderAllPropertiesDto() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public LocalDateTime getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(LocalDateTime orderTime) {
        this.orderTime = orderTime;
    }

    public UserWithoutRelationDto getOwner() {
        return owner;
    }

    public void setOwner(UserWithoutRelationDto owner) {
        this.owner = owner;
    }

    public Set<BucketWithoutOrderRelationDto> getBuckets() {
        return buckets;
    }

    public void setBuckets(Set<BucketWithoutOrderRelationDto> buckets) {
        this.buckets = buckets;
    }
}
