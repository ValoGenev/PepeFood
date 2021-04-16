package com.gegessen.service.product;

import com.gegessen.controller.OrderController;
import com.gegessen.dto.category.FoodCategoryWithCountDto;
import com.gegessen.dto.order.OrderWithoutRelationDto;
import com.gegessen.dto.product.ProductAllPropertiesDto;
import com.gegessen.dto.product.ProductWithoutRelationDto;
import com.gegessen.dto.restaurant.RestaurantAllPropertiesDto;
import com.gegessen.dto.restaurant.RestaurantWithoutRelationDto;
import org.slf4j.Logger;

import java.util.List;
import java.util.Set;

import static org.slf4j.LoggerFactory.getLogger;

public interface IProductService {

    Set<ProductWithoutRelationDto> findAll();

    ProductAllPropertiesDto findOne(String id);

    void delete(String id);

    ProductAllPropertiesDto create(ProductAllPropertiesDto product);

    ProductAllPropertiesDto update(ProductAllPropertiesDto product, String id);

    Set<OrderWithoutRelationDto> getProductOrders(String id);

    List<FoodCategoryWithCountDto> getCategoriesWithCount();
}
