package com.gegessen.service.restaurant;

import com.gegessen.dto.category.FoodCategoryWithCountDto;
import com.gegessen.dto.user.UserAllPropertiesDto;
import com.gegessen.dto.restaurant.RestaurantAllPropertiesDto;
import com.gegessen.dto.restaurant.RestaurantWithoutRelationDto;
import com.gegessen.entity.RestaurantEntity;
import com.gegessen.entity.UserEntity;
import com.gegessen.exception.AlreadyExistingResourceException;
import com.gegessen.exception.ConflictException;
import com.gegessen.exception.RestaurantNotFoundException;
import com.gegessen.exception.handlers.AlreadyOwningRestaurantException;
import com.gegessen.repository.IRestaurantRepository;
import com.gegessen.service.user.IUserService;

import org.hibernate.service.spi.ServiceException;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static com.gegessen.util.Constants.*;
import static java.lang.String.format;
import static java.util.Objects.isNull;


public class RestaurantService implements IRestaurantService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RestaurantService.class);

    private final IRestaurantRepository restaurantRepository;
    private final IUserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public RestaurantService(IRestaurantRepository restaurantRepository,IUserService userService,ModelMapper modelMapper) {
        this.restaurantRepository = restaurantRepository;
        this.userService=userService;
        this.modelMapper=modelMapper;
    }

    @Override
    public Set<RestaurantWithoutRelationDto> findAll() {
        LOGGER.info(GET_ALL_RESTAURANTS_MESSAGE);

        try {
            return restaurantRepository
                    .findAll()
                    .stream()
                    .map(restaurant -> modelMapper.map(restaurant, RestaurantWithoutRelationDto.class))
                    .collect(Collectors.toSet());
        } catch (DataAccessException e) {
            LOGGER.error(DATABASE_ERROR_MESSAGE);
            throw new ServiceException(DATABASE_ERROR_MESSAGE);
        }
    }

    @Override
    public RestaurantAllPropertiesDto findOne(String id) {
        LOGGER.info(format(FIND_RESTAURANT_BY_ID_MESSAGE,id));

        return modelMapper.map(findRestaurant(id), RestaurantAllPropertiesDto.class);
    }

    @Override
    public void delete(String id) {
        LOGGER.info(format(DELETE_RESTAURANT_BY_ID_MESSAGE, id));

        try {
            restaurantRepository.findById(id).ifPresent(restaurantRepository::delete);
        } catch (DataIntegrityViolationException e) {
            LOGGER.error(CONFLICT_DELETE_MESSAGE);
            throw new ConflictException(CONFLICT_DELETE_MESSAGE);
        } catch (DataAccessException e) {
            LOGGER.error(DATABASE_ERROR_MESSAGE);
            throw new ServiceException(DATABASE_ERROR_MESSAGE);
        }
    }

    @Override
    public RestaurantAllPropertiesDto create(RestaurantAllPropertiesDto restaurant) {
        LOGGER.info(format(CREATE_RESTAURANT_MESSAGE, restaurant.getOwner().getId()));

        RestaurantEntity restaurantToBeCreated = modelMapper.map(restaurant,RestaurantEntity.class);

        restaurantToBeCreated.setProducts(new HashSet<>());

        UserAllPropertiesDto userInDb = userService.findOne(restaurant.getOwner().getId());

        assertUserNotOwningRestaurant(userInDb);

        restaurantToBeCreated.setOwner(modelMapper.map(userInDb,UserEntity.class));

        return modelMapper.map(createRestaurant(restaurantToBeCreated), RestaurantAllPropertiesDto.class);
    }

    private void assertUserNotOwningRestaurant(UserAllPropertiesDto user) {
        if(!isNull(user.getRestaurant())){
            throw new AlreadyOwningRestaurantException(format(USER_ALREADY_OWNING_RESTAURANT,user.getId()));
        }
    }

    @Override
    public RestaurantAllPropertiesDto update(RestaurantAllPropertiesDto restaurant, String id) {
        LOGGER.info(format(UPDATE_RESTAURANT_BY_ID_MESSAGE,id));

        RestaurantEntity restaurantInDb = findRestaurant(id);

        RestaurantEntity restaurantToBeUpdated = modelMapper.map(restaurant,RestaurantEntity.class);

        restaurantToBeUpdated.setId(restaurantInDb.getId());

        UserAllPropertiesDto userInDb = userService.findOne(restaurant.getOwner().getId());

        restaurantToBeUpdated.setOwner(modelMapper.map(userInDb,UserEntity.class));
        restaurantToBeUpdated.setProducts(restaurantInDb.getProducts());

        return modelMapper.map(createRestaurant(restaurantToBeUpdated), RestaurantAllPropertiesDto.class);
    }


    private RestaurantEntity createRestaurant(RestaurantEntity restaurant) {
        try {
            return restaurantRepository.save(restaurant);
        } catch (DataIntegrityViolationException e) {
            LOGGER.error(CONFLICT_CREATE_MESSAGE);
            throw new AlreadyExistingResourceException(EXISTING_RESOURCE_MESSAGE);
        } catch (DataAccessException e) {
            LOGGER.error(DATABASE_ERROR_MESSAGE, e);
            throw new ServiceException(DATABASE_ERROR_MESSAGE);
        }
    }


    private RestaurantEntity findRestaurant(String id) {
        try {
            return restaurantRepository
                    .findById(id)
                    .orElseThrow(() -> new RestaurantNotFoundException(format(RESTAURANT_NOT_FOUND_MESSAGE,id)));
        } catch (DataAccessException e) {
            LOGGER.error(DATABASE_ERROR_MESSAGE);
            throw new ServiceException(DATABASE_ERROR_MESSAGE);
        }
    }

}
