package com.gegessen.service.order;

import com.gegessen.dto.order.OrderAllPropertiesDto;
import com.gegessen.dto.order.OrderWithoutRelationDto;
import com.gegessen.dto.product.ProductAllPropertiesDto;
import com.gegessen.dto.product.ProductWithoutRelationDto;

import java.util.Set;

public interface IOrderService {

    Set<OrderWithoutRelationDto> findAll();

    OrderAllPropertiesDto findOne(String id);

    void delete(String id);

    OrderAllPropertiesDto create(OrderAllPropertiesDto product);

    OrderAllPropertiesDto update(OrderAllPropertiesDto product, String id);
}
