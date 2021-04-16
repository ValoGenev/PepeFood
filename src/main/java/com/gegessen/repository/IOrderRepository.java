package com.gegessen.repository;

import com.gegessen.entity.OrderEntity;
import com.gegessen.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IOrderRepository extends JpaRepository<OrderEntity,String> {

}
