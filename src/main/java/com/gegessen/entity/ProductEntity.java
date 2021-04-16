package com.gegessen.entity;

import com.gegessen.model.ProductCategory;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="products")
public class ProductEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id")
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "category")
    private ProductCategory category;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "portionSize")
    private int portionSize;

    @Column(name = "pictureUrl")
    private String pictureUrl;

    @Column(name = "price")
    private double price;

    @ManyToOne(targetEntity = RestaurantEntity.class)
    @JoinColumn(name = "restaurant_id",referencedColumnName = "id")
    private RestaurantEntity restaurant;

    @OneToMany(targetEntity = BucketEntity.class,mappedBy = "product")
    private Set<BucketEntity> buckets;



    public ProductEntity() {
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

    public RestaurantEntity getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(RestaurantEntity restaurant) {
        this.restaurant = restaurant;
    }

    public Set<BucketEntity> getBuckets() {
        return buckets;
    }

    public void setBuckets(Set<BucketEntity> buckets) {
        this.buckets = buckets;
    }

//    public Set<OrderEntity> getOrders() {
//        return orders;
//    }
//
//    public void setOrders(Set<OrderEntity> orders) {
//        this.orders = orders;
//    }
}
