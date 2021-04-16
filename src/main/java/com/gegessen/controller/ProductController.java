package com.gegessen.controller;

import com.gegessen.dto.order.OrderWithoutRelationDto;
import com.gegessen.dto.product.ProductAllPropertiesDto;
import com.gegessen.dto.product.ProductWithoutRelationDto;
import com.gegessen.dto.restaurant.RestaurantAllPropertiesDto;
import com.gegessen.dto.restaurant.RestaurantWithoutRelationDto;
import com.gegessen.service.product.IProductService;
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
@RequestMapping("/config/api/v1/products")
public class ProductController {

    private static final Logger LOGGER = getLogger(ProductController.class);
    private final IProductService productService;

    @Autowired
    public ProductController(IProductService productService) {
        this.productService = productService;
    }


    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Set<ProductWithoutRelationDto>> getAll() {
        LOGGER.info(GET_ALL_PRODUCTS_MESSAGE);
        return ok(productService.findAll());
    }

    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductAllPropertiesDto> getOne(@PathVariable("id") String id) {
        LOGGER.info(format(FIND_PRODUCT_BY_ID_MESSAGE, id));
        return ok(productService.findOne(id));
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductAllPropertiesDto> create(@Valid @RequestBody ProductAllPropertiesDto product) {
        LOGGER.info(format(CREATE_PRODUCT_MESSAGE, product.getRestaurant().getId()));
        return status(CREATED).body(productService.create(product));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") String id) {
        LOGGER.info(format(DELETE_PRODUCT_BY_ID_MESSAGE, id));
        productService.delete(id);
        return status(NO_CONTENT).build();
    }

    @PutMapping(value = "/{id}", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductAllPropertiesDto> update(@Valid @RequestBody ProductAllPropertiesDto product, @PathVariable("id") String id) {
        LOGGER.info(format(UPDATE_PRODUCT_BY_ID_MESSAGE, id));
        return ok(productService.update(product, id));
    }

    @GetMapping(value = "/{id}/orders", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Set<OrderWithoutRelationDto>> getProductOrders(@PathVariable("id") String id) {
        LOGGER.info("GETTING PRODUCT ORDERS");
        return ok(productService.getProductOrders(id));
    }


}
