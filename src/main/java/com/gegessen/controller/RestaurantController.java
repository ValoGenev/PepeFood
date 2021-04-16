package com.gegessen.controller;

import com.gegessen.dto.restaurant.RestaurantAllPropertiesDto;
import com.gegessen.dto.restaurant.RestaurantWithoutRelationDto;
import com.gegessen.service.restaurant.IRestaurantService;
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
@RequestMapping("/config/api/v1/restaurants")
public class RestaurantController {

    private static final Logger LOGGER = getLogger(RestaurantController.class);
    private final IRestaurantService restaurantService;

    @Autowired
    public RestaurantController(IRestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Set<RestaurantWithoutRelationDto>> getAll() {
        LOGGER.info(GET_ALL_RESTAURANTS_MESSAGE);
        return ok(restaurantService.findAll());
    }

    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<RestaurantAllPropertiesDto> getOne(@PathVariable("id") String id) {
        LOGGER.info(format(FIND_RESTAURANT_BY_ID_MESSAGE, id));
        return ok(restaurantService.findOne(id));
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<RestaurantAllPropertiesDto> create(@Valid @RequestBody RestaurantAllPropertiesDto restaurant) {
        LOGGER.info(format(CREATE_RESTAURANT_MESSAGE, restaurant.getOwner().getEmail()));
        return status(CREATED).body(restaurantService.create(restaurant));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") String id) {
        LOGGER.info(format(DELETE_RESTAURANT_BY_ID_MESSAGE, id));
        restaurantService.delete(id);
        return status(NO_CONTENT).build();
    }

    @PutMapping(value = "/{id}", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<RestaurantAllPropertiesDto> update(@Valid @RequestBody RestaurantAllPropertiesDto restaurant, @PathVariable("id") String id) {
        LOGGER.info(format(UPDATE_RESTAURANT_BY_ID_MESSAGE, id));
        return ok(restaurantService.update(restaurant, id));
    }


}
