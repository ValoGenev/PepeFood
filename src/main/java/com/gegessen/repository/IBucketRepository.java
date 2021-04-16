package com.gegessen.repository;

import com.gegessen.entity.BucketEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IBucketRepository extends JpaRepository<BucketEntity,String> {
}
