package com.gegessen.controller;

import com.gegessen.dto.order.OrderAllPropertiesDto;
import com.gegessen.dto.order.OrderWithoutRelationDto;
import com.gegessen.dto.product.ProductAllPropertiesDto;
import com.gegessen.dto.product.ProductWithoutRelationDto;
import com.gegessen.service.order.IOrderService;
import com.gegessen.service.product.IProductService;
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
@RequestMapping("/config/api/v1/orders")
public class OrderController {

    private static final Logger LOGGER = getLogger(OrderController.class);
    private final IOrderService orderService;

    @Autowired
    public OrderController(IOrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Set<OrderWithoutRelationDto>> getAll() {
        LOGGER.info(GET_ALL_ORDERS_MESSAGE);
        return ok(orderService.findAll());
    }

    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<OrderAllPropertiesDto> getOne(@PathVariable("id") String id) {
        LOGGER.info(format(FIND_ORDER_BY_ID_MESSAGE, id));
        return ok(orderService.findOne(id));
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<OrderAllPropertiesDto> create(@Valid @RequestBody OrderAllPropertiesDto order) {
        LOGGER.info(format(CREATE_ORDER_MESSAGE, order.getOwner().getEmail()));
        return status(CREATED).body(orderService.create(order));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") String id) {
        LOGGER.info(format(DELETE_ORDER_BY_ID_MESSAGE, id));
        orderService.delete(id);
        return status(NO_CONTENT).build();
    }

    @PutMapping(value = "/{id}", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<OrderAllPropertiesDto> update(@Valid @RequestBody OrderAllPropertiesDto order, @PathVariable("id") String id) {
        LOGGER.info(format(UPDATE_ORDER_BY_ID_MESSAGE, id));
        return ok(orderService.update(order, id));
    }
}
