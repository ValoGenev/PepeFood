package com.gegessen.dto.bucket;

import com.gegessen.dto.order.OrderWithoutRelationDto;
import com.gegessen.dto.product.ProductWithoutRelationDto;
import com.gegessen.entity.OrderEntity;
import com.gegessen.entity.ProductEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import java.util.Set;

public class BucketAllPropertiesDto {

    private String id;

    @Min(value = 0,message = "quantity must not be negative number")
    private int quantity;

    private ProductWithoutRelationDto product;

    private OrderWithoutRelationDto order;

    public BucketAllPropertiesDto(String id, int quantity, ProductWithoutRelationDto product, OrderWithoutRelationDto order) {
        this.id = id;
        this.quantity = quantity;
        this.product = product;
        this.order = order;
    }

    public BucketAllPropertiesDto() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public ProductWithoutRelationDto getProduct() {
        return product;
    }

    public void setProduct(ProductWithoutRelationDto product) {
        this.product = product;
    }

    public OrderWithoutRelationDto getOrder() {
        return order;
    }

    public void setOrder(OrderWithoutRelationDto order) {
        this.order = order;
    }
}
