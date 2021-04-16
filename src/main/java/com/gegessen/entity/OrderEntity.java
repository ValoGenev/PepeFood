package com.gegessen.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name="orders")
public class OrderEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id")
    private String id;

    @Column(name = "totalPrice")
    private double totalPrice;

    @Column(name = "orderTime")
    private LocalDateTime orderTime;

    @ManyToOne(targetEntity = UserEntity.class)
    @JoinColumn(name = "owner_id",referencedColumnName = "id")
    private UserEntity owner;

    @OneToMany(mappedBy = "order",targetEntity = BucketEntity.class,fetch = FetchType.EAGER)
    private Set<BucketEntity> buckets;

//    @ManyToMany(targetEntity = ProductEntity.class)
//    @JoinTable(name="products_orders",
//            joinColumns = @JoinColumn(name="order_id",referencedColumnName = "id"),
//            inverseJoinColumns = @JoinColumn(name="product_id",referencedColumnName = "id"))
//    private Set<ProductEntity> products;


    public OrderEntity() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDateTime getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(LocalDateTime orderTime) {
        this.orderTime = orderTime;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public UserEntity getOwner() {
        return owner;
    }

    public void setOwner(UserEntity owner) {
        this.owner = owner;
    }

//    public Set<ProductEntity> getProducts() {
//        return products;
//    }
//
//    public void setProducts(Set<ProductEntity> products) {
//        this.products = products;
//    }


    public Set<BucketEntity> getBuckets() {
        return buckets;
    }

    public void setBuckets(Set<BucketEntity> buckets) {
        this.buckets = buckets;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderEntity that = (OrderEntity) o;
        return Double.compare(that.totalPrice, totalPrice) == 0 &&
                id.equals(that.id) &&
                orderTime.equals(that.orderTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, totalPrice, orderTime);
    }
}
