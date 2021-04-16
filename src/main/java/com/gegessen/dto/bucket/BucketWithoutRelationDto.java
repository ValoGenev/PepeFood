package com.gegessen.dto.bucket;

import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;

public class BucketWithoutRelationDto {

    private String id;

    @Min(value = 0,message = "quantity must not be negative number")
    private int quantity;

    public BucketWithoutRelationDto(String id, int quantity) {
        this.id = id;
        this.quantity = quantity;
    }

    public BucketWithoutRelationDto() {
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
}
