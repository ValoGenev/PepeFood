package com.gegessen.dto.product;

import com.gegessen.dto.order.OrderWithoutRelationDto;
import com.gegessen.dto.restaurant.RestaurantWithoutRelationDto;
import com.gegessen.model.ProductCategory;

import java.util.Set;

public class ProductWithoutRelationDto {

    private String id;

    private String name;

    private ProductCategory category;

    private int quantity;

    private int portionSize;

    private String pictureUrl;

    private double price;

    public ProductWithoutRelationDto(
            String id,
            String name,
            ProductCategory category,
            int quantity,
            int portionSize,
            String pictureUrl,
            double price
    ) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.quantity = quantity;
        this.portionSize = portionSize;
        this.pictureUrl = pictureUrl;
        this.price = price;
    }

    public ProductWithoutRelationDto() {
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
}
