package com.gegessen.repository;

import com.gegessen.entity.OrderEntity;
import com.gegessen.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface IProductRepository extends JpaRepository<ProductEntity,String> {


    @Query(value = "select distinct o from OrderEntity as o inner join o.buckets as b inner join b.product as p where p.id=:id")
    Set<OrderEntity> findPlease(@Param("id") String id);

    @Query(value = "select p from ProductEntity as p where p.quantity > 0")
    List<ProductEntity> getAvailableProducts();

}
