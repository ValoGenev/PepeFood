package com.gegessen.model;

public enum  ProductCategory {

    // italian, mexican, asian, vegan

    BURGER(""),
    FINE_DINING(""),
    BRUNCH(""),
    GROCERY(""),
    VEGETARIAN(""),
    STREET_FOOD(""),
    GREEK(""),
    HEALTHY(""),
    FISH(""),
    PIZZA(""),
    VEGAN(""),
    ITALIAN("https://italian"),
    ASIAN("https://chinese"),
    BULGARIAN("https://bulgarian"),
    MEXICAN(""),
    SPICY("https://spicy");

    private String url;

    ProductCategory(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
