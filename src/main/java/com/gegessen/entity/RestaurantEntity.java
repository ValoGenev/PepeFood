package com.gegessen.entity;

import com.gegessen.model.DayOfTheWeek;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.Set;

@Entity
@Table(name="restaurants")
public class RestaurantEntity {

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

    @Column(name = "description",columnDefinition="text", length=50000)
    private String description;

    @Column(name = "phoneNumber")
    private String phoneNumber;

    @Column(name = "x_coordinate")
    private String xCoordinate;

    @Column(name = "y_coordinate")
    private String yCoordinate;

    @Column(name = "opening_time_of_the_day")
    private LocalTime openingTimeOfTheDay;

    @Column(name = "closing_time_of_the_day")
    private LocalTime closingTimeOfTheDay;

    @Column(name = "opening_day_of_the_day")
    private DayOfTheWeek openingDayOfTheWeek;

    @Column(name = "closing_day_of_the_day")
    private DayOfTheWeek closingDayOfTheWeek;

    @Column(name = "short_description",columnDefinition="text", length=50000)
    private String shortDescription;

    @Column(name = "city")
    private String city;

    @Column(name = "neighborhood")
    private String neighborhood;

    @Column(name = "street")
    private String street;

    @OneToOne(targetEntity = UserEntity.class)
    @JoinColumn(name = "owner_id",referencedColumnName = "id")
    private UserEntity owner;

    @OneToMany(targetEntity = ProductEntity.class,mappedBy = "restaurant")
    private Set<ProductEntity> products;


    public RestaurantEntity() {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public UserEntity getOwner() {
        return owner;
    }

    public void setOwner(UserEntity owner) {
        this.owner = owner;
    }

    public Set<ProductEntity> getProducts() {
        return products;
    }

    public void setProducts(Set<ProductEntity> products) {
        this.products = products;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getXCoordinate() {
        return xCoordinate;
    }

    public void setXCoordinate(String xCoordinate) {
        this.xCoordinate = xCoordinate;
    }

    public String getYCoordinate() {
        return yCoordinate;
    }

    public void setYCoordinate(String yCoordinate) {
        this.yCoordinate = yCoordinate;
    }

    public LocalTime getOpeningTimeOfTheDay() {
        return openingTimeOfTheDay;
    }

    public void setOpeningTimeOfTheDay(LocalTime openingTimeOfTheDay) {
        this.openingTimeOfTheDay = openingTimeOfTheDay;
    }

    public LocalTime getClosingTimeOfTheDay() {
        return closingTimeOfTheDay;
    }

    public void setClosingTimeOfTheDay(LocalTime closingTimeOfTheDay) {
        this.closingTimeOfTheDay = closingTimeOfTheDay;
    }

    public DayOfTheWeek getOpeningDayOfTheWeek() {
        return openingDayOfTheWeek;
    }

    public void setOpeningDayOfTheWeek(DayOfTheWeek openingDayOfTheWeek) {
        this.openingDayOfTheWeek = openingDayOfTheWeek;
    }

    public DayOfTheWeek getClosingDayOfTheWeek() {
        return closingDayOfTheWeek;
    }

    public void setClosingDayOfTheWeek(DayOfTheWeek closingDayOfTheWeek) {
        this.closingDayOfTheWeek = closingDayOfTheWeek;
    }
}
