package com.gegessen.service.order;

import com.gegessen.dto.user.UserAllPropertiesDto;
import com.gegessen.dto.bucket.BucketAllPropertiesDto;
import com.gegessen.dto.order.OrderAllPropertiesDto;
import com.gegessen.dto.order.OrderWithoutRelationDto;
import com.gegessen.entity.BucketEntity;
import com.gegessen.entity.OrderEntity;
import com.gegessen.entity.UserEntity;
import com.gegessen.exception.AlreadyExistingResourceException;
import com.gegessen.exception.ConflictException;
import com.gegessen.exception.ProductNotFoundException;
import com.gegessen.repository.IOrderRepository;
import com.gegessen.service.bucket.IBucketService;
import com.gegessen.service.product.IProductService;
import com.gegessen.service.user.IUserService;
import org.hibernate.service.spi.ServiceException;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static com.gegessen.util.Constants.*;
import static java.lang.String.format;

public class OrderService implements IOrderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderService.class);
    private final IOrderRepository orderRepository;
    private final IUserService userService;
    private final IBucketService bucketService;
    private final ModelMapper modelMapper;

    @Autowired
    @Lazy
    private IProductService productService;

    @Autowired
    public OrderService(IOrderRepository orderRepository, IUserService userService, IBucketService bucketService, ModelMapper modelMapper) {
        this.orderRepository = orderRepository;
        this.userService=userService;
        this.bucketService = bucketService;
        this.modelMapper = modelMapper;
    }

    @Override
    public Set<OrderWithoutRelationDto> findAll() {
        LOGGER.info(GET_ALL_PRODUCTS_MESSAGE);

        try {
            return orderRepository
                    .findAll()
                    .stream()
                    .map(product -> modelMapper.map(product, OrderWithoutRelationDto.class))
                    .collect(Collectors.toSet());
        } catch (DataAccessException e) {
            LOGGER.error(DATABASE_ERROR_MESSAGE);
            throw new ServiceException(DATABASE_ERROR_MESSAGE);
        }
    }

    @Override
    public OrderAllPropertiesDto findOne(String id) {
        LOGGER.info(format(FIND_ORDER_BY_ID_MESSAGE, id));

        return modelMapper.map(findOrder(id), OrderAllPropertiesDto.class);
    }

    @Override
    @Transactional
    public void delete(String id) {
        LOGGER.info(format(DELETE_ORDER_BY_ID_MESSAGE, id));

        try {
            orderRepository.findById(id).ifPresent((order)-> {
                order.getBuckets().forEach(bucket-> bucketService.delete(bucket.getId()));
                orderRepository.delete(order);
            });
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
    public OrderAllPropertiesDto create(OrderAllPropertiesDto order) {
        LOGGER.info(format(CREATE_ORDER_MESSAGE, order.getOwner().getId()));

        OrderEntity orderToBeCreated = modelMapper.map(order, OrderEntity.class);

        Set<BucketEntity> createdBuckets = new HashSet<>();

        order.setTotalPrice(0.0);

        UserAllPropertiesDto userInDb = userService.findOne(order.getOwner().getId());

        double totalPrice = order.getBuckets().stream().mapToDouble(b->{

           double price =  productService.findOne(b.getProduct().getId()).getPrice() * b.getQuantity();
           order.setTotalPrice(order.getTotalPrice()+price);
           return price;

        }).sum();

        orderToBeCreated.setTotalPrice(totalPrice);

        orderToBeCreated.setOrderTime(LocalDateTime.now());

        orderToBeCreated.setBuckets(createdBuckets);

        orderToBeCreated.setOwner(modelMapper.map(userInDb,UserEntity.class));

        OrderEntity createdOrder = createOrder(orderToBeCreated);

        order.getBuckets().forEach(bucket->{
            BucketAllPropertiesDto bucketToBeCreated = modelMapper.map(bucket,BucketAllPropertiesDto.class);
            bucketToBeCreated.setOrder(modelMapper.map(createdOrder,OrderWithoutRelationDto.class));
            createdBuckets.add(modelMapper.map(bucketService.create(bucketToBeCreated),BucketEntity.class));
        });

        createdOrder.setBuckets(createdBuckets);

        return modelMapper.map(createdOrder,OrderAllPropertiesDto.class);
    }

    @Override
    public OrderAllPropertiesDto update(OrderAllPropertiesDto order, String id) {
        LOGGER.info(format(UPDATE_ORDER_BY_ID_MESSAGE, order.getOwner().getEmail()));

        OrderEntity orderInDb = findOrder(id);

        OrderEntity orderToBeUpdated = modelMapper.map(order, OrderEntity.class);

        UserAllPropertiesDto userInDb = userService.findOne(order.getOwner().getId());

        orderToBeUpdated.setId(orderInDb.getId());
        orderToBeUpdated.setTotalPrice(orderInDb.getTotalPrice());
        orderToBeUpdated.setBuckets(orderInDb.getBuckets());

        orderToBeUpdated.setOwner(modelMapper.map(userInDb,UserEntity.class));

        return modelMapper.map(createOrder(orderToBeUpdated),OrderAllPropertiesDto.class);
    }

    private OrderEntity createOrder(OrderEntity order) {
        try {
            return orderRepository.save(order);

        } catch (DataIntegrityViolationException e) {
            LOGGER.error(CONFLICT_CREATE_MESSAGE);
            throw new AlreadyExistingResourceException(EXISTING_RESOURCE_MESSAGE);
        } catch (DataAccessException e) {
            LOGGER.error(DATABASE_ERROR_MESSAGE, e);
            throw new ServiceException(DATABASE_ERROR_MESSAGE);
        }
    }


    private OrderEntity findOrder(String id) {
        try {
            return orderRepository
                    .findById(id)
                    .orElseThrow(() -> new ProductNotFoundException(format(PRODUCT_NOT_FOUND_MESSAGE, id)));
        } catch (DataAccessException e) {
            LOGGER.error(DATABASE_ERROR_MESSAGE);
            throw new ServiceException(DATABASE_ERROR_MESSAGE);
        }
    }
}
