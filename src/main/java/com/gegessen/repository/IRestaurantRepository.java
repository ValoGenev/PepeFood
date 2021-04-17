package com.gegessen.repository;

import com.gegessen.entity.RestaurantEntity;
import com.gegessen.model.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface IRestaurantRepository extends JpaRepository<RestaurantEntity,String> {

//    Set<RestaurantEntity> getRestaurant

    @Query("select distinct r from RestaurantEntity as r inner join r.products as p where p.category = :category")
    List<RestaurantEntity> findAllByCategory(@Param("category") ProductCategory category);
}
