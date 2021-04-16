package com.gegessen.repository;

import com.gegessen.entity.RestaurantEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface IRestaurantRepository extends JpaRepository<RestaurantEntity,String> {

//    Set<RestaurantEntity> getRestaurant
}
