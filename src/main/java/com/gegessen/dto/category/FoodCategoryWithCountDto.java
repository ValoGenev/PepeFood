package com.gegessen.dto.category;

import java.util.Objects;

public class FoodCategoryWithCountDto {

    private String category;
    private String url;
    private int count;
    

    public FoodCategoryWithCountDto(String category, String url, int count) {
        this.category = category;
        this.url = url;
        this.count = count;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FoodCategoryWithCountDto that = (FoodCategoryWithCountDto) o;
        return count == that.count &&
                category.equals(that.category) &&
                url.equals(that.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(category, url, count);
    }
}
