package com.gegessen.controller;

import com.gegessen.dto.bucket.BucketAllPropertiesDto;
import com.gegessen.dto.bucket.BucketWithoutOrderRelationDto;
import com.gegessen.dto.bucket.BucketWithoutRelationDto;
import com.gegessen.dto.order.OrderAllPropertiesDto;
import com.gegessen.dto.order.OrderWithoutRelationDto;
import com.gegessen.service.bucket.IBucketService;
import com.gegessen.service.order.IOrderService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.Set;

import static com.gegessen.util.Constants.*;
import static java.lang.String.format;
import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/config/api/v1/buckets")
public class BucketController {

    private static final Logger LOGGER = getLogger(BucketController.class);
    private final IBucketService bucketService;

    @Autowired
    public BucketController(IBucketService bucketService) {
        this.bucketService = bucketService;
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Set<BucketWithoutRelationDto>> getAll() {
        LOGGER.info(GET_ALL_BUCKETS_MESSAGE);
        return ok(bucketService.findAll());
    }

    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<BucketAllPropertiesDto> getOne(@PathVariable("id") String id) {
        LOGGER.info(format(FIND_BUCKET_BY_ID_MESSAGE, id));
        return ok(bucketService.findOne(id));
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<BucketAllPropertiesDto> create(@Valid @RequestBody BucketAllPropertiesDto bucket) {
        LOGGER.info(format(CREATE_BUCKET_MESSAGE, bucket.getProduct().getId()));
        return status(CREATED).body(bucketService.create(bucket));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") String id) {
        LOGGER.info(format(DELETE_BUCKET_BY_ID_MESSAGE, id));
        bucketService.delete(id);
        return status(NO_CONTENT).build();
    }

    @PutMapping(value = "/{id}", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<BucketAllPropertiesDto> update(@Valid @RequestBody BucketAllPropertiesDto bucket, @PathVariable("id") String id) {
        LOGGER.info(format(UPDATE_BUCKET_BY_ID_MESSAGE, id));
        return ok(bucketService.update(bucket, id));
    }
}
