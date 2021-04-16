package com.gegessen.dto.restaurant;

import com.gegessen.model.DayOfTheWeek;

import java.time.LocalTime;

public class RestaurantWithoutRelationDto {

    private String id;

    private String name;

    private String description;

    private String phoneNumber;

    private String picUrl;

    private String city;

    private String neighborhood;

    private String street;

    private String xCoordinate;

    private String yCoordinate;

    private LocalTime openingTimeOfTheDay;

    private LocalTime closingTimeOfTheDay;

    private DayOfTheWeek openingDayOfTheWeek;

    private DayOfTheWeek closingDayOfTheWeek;

    private String shortDescription;

    public RestaurantWithoutRelationDto() {
    }

    public RestaurantWithoutRelationDto(
            String id,
            String name,
            String description,
            String phoneNumber,
            String city,
            String neighborhood,
            String street
    ) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.phoneNumber = phoneNumber;
        this.city = city;
        this.neighborhood = neighborhood;
        this.street = street;
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

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public DayOfTheWeek getClosingDayOfTheWeek() {
        return closingDayOfTheWeek;
    }

    public void setClosingDayOfTheWeek(DayOfTheWeek closingDayOfTheWeek) {
        this.closingDayOfTheWeek = closingDayOfTheWeek;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }
}
