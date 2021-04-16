package com.gegessen.dto.bucket;

import com.gegessen.dto.order.OrderWithoutRelationDto;
import com.gegessen.dto.product.ProductWithoutRelationDto;

import javax.validation.constraints.Min;
import java.util.Set;

public class BucketWithoutOrderRelationDto {

    private String id;

    @Min(value = 0,message = "quantity must not be negative number")
    private int quantity;

    private ProductWithoutRelationDto product;

    public BucketWithoutOrderRelationDto(String id, int quantity, ProductWithoutRelationDto product) {
        this.id = id;
        this.quantity = quantity;
        this.product = product;
    }

    public BucketWithoutOrderRelationDto() {
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
}
