package com.gegessen.service.restaurant;

import com.gegessen.dto.category.FoodCategoryWithCountDto;
import com.gegessen.dto.restaurant.RestaurantAllPropertiesDto;
import com.gegessen.dto.restaurant.RestaurantWithoutRelationDto;

import java.util.Set;

public interface IRestaurantService {

    Set<RestaurantWithoutRelationDto> findAll();

    RestaurantAllPropertiesDto findOne(String id);

    void delete(String id);

    RestaurantAllPropertiesDto create(RestaurantAllPropertiesDto restaurant);

    RestaurantAllPropertiesDto update(RestaurantAllPropertiesDto restaurant, String id);


}
