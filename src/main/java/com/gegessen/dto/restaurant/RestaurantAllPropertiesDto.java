package com.gegessen.dto.restaurant;

import com.gegessen.dto.user.UserWithoutRelationDto;
import com.gegessen.dto.product.ProductWithoutRelationDto;
import com.gegessen.model.DayOfTheWeek;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.time.LocalTime;
import java.util.Set;

public class RestaurantAllPropertiesDto {

    @Null(message = "id must be null")
    private String id;

    @NotBlank(message = "name cannot be empty or null")
    private String name;

    private String description;

    private String picUrl;

    @NotBlank(message = "phone number cannot be empty or null") //TODO add regex
    private String phoneNumber;

    @NotBlank(message = "city  cannot be empty or null")
    private String city;

    @NotBlank(message = "neighborhood cannot be empty or null")
    private String neighborhood;

    @NotBlank(message = "street cannot be empty or null")
    private String street;

    private String xCoordinate;

    private String yCoordinate;

    private LocalTime openingTimeOfTheDay;

    private LocalTime closingTimeOfTheDay;

    private DayOfTheWeek openingDayOfTheWeek;

    private DayOfTheWeek closingDayOfTheWeek;

    private String shortDescription;

    @NotNull(message = "owner cannot be empty or null")
    private UserWithoutRelationDto owner;

    private Set<ProductWithoutRelationDto> products;

    public RestaurantAllPropertiesDto() {
    }

    public RestaurantAllPropertiesDto(
            String id,
            String name,
            String description,
            String phoneNumber,
            String city,
            String neighborhood,
            String street,
            UserWithoutRelationDto owner,
            Set<ProductWithoutRelationDto> products
    ) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.phoneNumber = phoneNumber;
        this.city = city;
        this.neighborhood = neighborhood;
        this.street = street;
        this.owner = owner;
        this.products = products;
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

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
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

    public UserWithoutRelationDto getOwner() {
        return owner;
    }

    public void setOwner(UserWithoutRelationDto owner) {
        this.owner = owner;
    }

    public Set<ProductWithoutRelationDto> getProducts() {
        return products;
    }

    public void setProducts(Set<ProductWithoutRelationDto> products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return "RestaurantAllPropertiesDto{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", city='" + city + '\'' +
                ", neighborhood='" + neighborhood + '\'' +
                ", street='" + street + '\'' +
                ", xCoordinate='" + xCoordinate + '\'' +
                ", yCoordinate='" + yCoordinate + '\'' +
                ", openingTimeOfTheDay=" + openingTimeOfTheDay +
                ", closingTimeOfTheDay=" + closingTimeOfTheDay +
                ", openingDayOfTheWeek=" + openingDayOfTheWeek +
                ", closingDayOfTheWeek=" + closingDayOfTheWeek +
                ", shortDescription='" + shortDescription + '\'' +
                ", owner=" + owner +
                ", products=" + products +
                '}';
    }
}
