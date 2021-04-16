package com.gegessen.service.bucket;

import com.gegessen.dto.bucket.BucketAllPropertiesDto;
import com.gegessen.dto.bucket.BucketWithoutOrderRelationDto;
import com.gegessen.dto.bucket.BucketWithoutRelationDto;

import java.util.Set;

public interface IBucketService {

    Set<BucketWithoutRelationDto> findAll();

    BucketAllPropertiesDto findOne(String id);

    void delete(String id);

    BucketAllPropertiesDto create(BucketAllPropertiesDto bucket);

    BucketAllPropertiesDto update(BucketAllPropertiesDto bucket, String id);
}
