package com.gegessen.dto.order;

import com.gegessen.entity.ProductEntity;
import com.gegessen.entity.UserEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

public class OrderWithoutRelationDto {

    private String id;

    private double totalPrice;

    private LocalDateTime orderTime;

    public OrderWithoutRelationDto(
            String id,
            double totalPrice,
            LocalDateTime orderTime
    ) {
        this.id = id;
        this.totalPrice = totalPrice;
        this.orderTime = orderTime;
    }

    public OrderWithoutRelationDto() {
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
}
