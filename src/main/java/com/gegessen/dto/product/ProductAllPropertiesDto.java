package com.gegessen.dto.product;

import com.gegessen.dto.bucket.BucketWithoutOrderRelationDto;
import com.gegessen.dto.order.OrderWithoutRelationDto;
import com.gegessen.dto.restaurant.RestaurantWithoutRelationDto;
import com.gegessen.entity.OrderEntity;
import com.gegessen.entity.RestaurantEntity;
import com.gegessen.model.ProductCategory;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Set;

public class ProductAllPropertiesDto {

    private String id;

    private String name;

    private ProductCategory category;

    @Min(value = 0,message = "quantity must not be negative number")
    private int quantity;

    @Min(value = 0,message = "portion size must not be negative number")
    private int portionSize;

    private String pictureUrl;

    @Min(value = 0,message = "price must not be negative number")
    private double price;

    @NotNull(message = "restaurant cannot be null")
    private RestaurantWithoutRelationDto restaurant;

    private Set<BucketWithoutOrderRelationDto> buckets;

    public ProductAllPropertiesDto(
            String id,
            String name,
            ProductCategory category,
            int quantity,
            int portionSize,
            String pictureUrl,
            double price,
            RestaurantWithoutRelationDto restaurant,
            Set<BucketWithoutOrderRelationDto> buckets
    ) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.quantity = quantity;
        this.portionSize = portionSize;
        this.pictureUrl = pictureUrl;
        this.price = price;
        this.restaurant = restaurant;
        this.buckets = buckets;
    }

    public ProductAllPropertiesDto() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ProductCategory getCategory() {
        return category;
    }

    public void setCategory(ProductCategory category) {
        this.category = category;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPortionSize() {
        return portionSize;
    }

    public void setPortionSize(int portionSize) {
        this.portionSize = portionSize;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public RestaurantWithoutRelationDto getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(RestaurantWithoutRelationDto restaurant) {
        this.restaurant = restaurant;
    }

    public Set<BucketWithoutOrderRelationDto> getBuckets() {
        return buckets;
    }

    public void setBuckets(Set<BucketWithoutOrderRelationDto> buckets) {
        this.buckets = buckets;
    }
}
