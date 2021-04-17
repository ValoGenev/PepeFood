package com.gegessen.service.restaurant;

import com.gegessen.dto.category.FoodCategoryWithCountDto;
import com.gegessen.dto.restaurant.RestaurantAllPropertiesDto;
import com.gegessen.dto.restaurant.RestaurantTestDto;
import com.gegessen.dto.restaurant.RestaurantWithoutRelationDto;
import com.gegessen.model.ProductCategory;

import java.util.List;
import java.util.Set;

public interface IRestaurantService {

    List<RestaurantTestDto> findAll();

    RestaurantAllPropertiesDto findOne(String id);

    void delete(String id);

    RestaurantAllPropertiesDto create(RestaurantAllPropertiesDto restaurant);

    RestaurantAllPropertiesDto update(RestaurantAllPropertiesDto restaurant, String id);
//
//    Set<RestaurantService>

    List<RestaurantTestDto> findAllByCategory(ProductCategory category);


}
