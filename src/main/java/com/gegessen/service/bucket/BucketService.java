package com.gegessen.service.bucket;

import com.gegessen.dto.bucket.BucketAllPropertiesDto;
import com.gegessen.dto.bucket.BucketWithoutOrderRelationDto;
import com.gegessen.dto.bucket.BucketWithoutRelationDto;
import com.gegessen.dto.order.OrderAllPropertiesDto;
import com.gegessen.dto.order.OrderWithoutRelationDto;
import com.gegessen.dto.product.ProductAllPropertiesDto;
import com.gegessen.entity.BucketEntity;
import com.gegessen.entity.OrderEntity;
import com.gegessen.entity.ProductEntity;
import com.gegessen.exception.*;
import com.gegessen.repository.IBucketRepository;
import com.gegessen.service.order.OrderService;
import com.gegessen.service.product.IProductService;
import org.hibernate.service.spi.ServiceException;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static com.gegessen.util.Constants.*;
import static java.lang.String.format;

public class BucketService implements IBucketService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BucketService.class);

    private final IBucketRepository bucketRepository;
    private final IProductService productService;
    private final ModelMapper modelMapper;


    @Autowired
    public BucketService(IBucketRepository bucketRepository,IProductService productService,ModelMapper modelMapper) {
        this.bucketRepository = bucketRepository;
        this.productService=productService;
        this.modelMapper=modelMapper;
    }

    @Override
    public Set<BucketWithoutRelationDto> findAll() {
        LOGGER.info(GET_ALL_BUCKETS_MESSAGE);

        try {
            return bucketRepository
                    .findAll()
                    .stream()
                    .map(bucket -> modelMapper.map(bucket, BucketWithoutRelationDto.class))
                    .collect(Collectors.toSet());
        } catch (DataAccessException e) {
            LOGGER.error(DATABASE_ERROR_MESSAGE);
            throw new ServiceException(DATABASE_ERROR_MESSAGE);
        }
    }

    @Override
    public BucketAllPropertiesDto findOne(String id) {
        LOGGER.info(format(FIND_BUCKET_BY_ID_MESSAGE, id));

        return modelMapper.map(findBucket(id), BucketAllPropertiesDto.class);
    }

    @Override
    public void delete(String id) {
        LOGGER.info(format(DELETE_BUCKET_BY_ID_MESSAGE, id));

        try {
            bucketRepository.findById(id).ifPresent(bucketRepository::delete);
        } catch (DataIntegrityViolationException e) {
            LOGGER.error(CONFLICT_DELETE_MESSAGE);
            throw new ConflictException(CONFLICT_DELETE_MESSAGE);
        } catch (DataAccessException e) {
            LOGGER.error(DATABASE_ERROR_MESSAGE);
            throw new ServiceException(DATABASE_ERROR_MESSAGE);
        }
    }

    @Override
    @Transactional
    public BucketAllPropertiesDto create(BucketAllPropertiesDto bucket) {
       LOGGER.info(format(CREATE_BUCKET_MESSAGE,bucket.getProduct().getId()));

       ProductAllPropertiesDto productInDb = productService.findOne(bucket.getProduct().getId());

       assertValidPurchaseQuantity(productInDb.getQuantity(),bucket.getQuantity());

       productInDb.setQuantity(productInDb.getQuantity()-bucket.getQuantity());

       productService.update(productInDb,productInDb.getId());

       BucketEntity bucketToBeCreated = modelMapper.map(bucket,BucketEntity.class);

       bucketToBeCreated.setProduct(modelMapper.map(productInDb, ProductEntity.class));

       return modelMapper.map(createBucket(bucketToBeCreated),BucketAllPropertiesDto.class);
    }

    private void assertValidPurchaseQuantity(int productQuantity,int purchaseQuantity) {
        if(productQuantity < purchaseQuantity){
            throw new InvalidPurchaseQuantityException("INVALID QUANTITY ");
        }
    }

    @Override
    public BucketAllPropertiesDto update(BucketAllPropertiesDto bucket, String id) {
        LOGGER.info(format(UPDATE_BUCKET_BY_ID_MESSAGE,bucket.getProduct().getId()));

        return null;
    }

    private BucketEntity createBucket(BucketEntity bucket) {
        try {
            return bucketRepository.save(bucket);

        } catch (DataIntegrityViolationException e) {
            LOGGER.error(CONFLICT_CREATE_MESSAGE);
            throw new AlreadyExistingResourceException(EXISTING_RESOURCE_MESSAGE);
        } catch (DataAccessException e) {
            LOGGER.error(DATABASE_ERROR_MESSAGE, e);
            throw new ServiceException(DATABASE_ERROR_MESSAGE);
        }
    }


    private BucketEntity findBucket(String id) {
        try {
            return bucketRepository
                    .findById(id)
                    .orElseThrow(() -> new BucketNotFoundException(format(BUCKET_NOT_FOUND_MESSAGE, id)));
        } catch (DataAccessException e) {
            LOGGER.error(DATABASE_ERROR_MESSAGE);
            throw new ServiceException(DATABASE_ERROR_MESSAGE);
        }
    }
}
