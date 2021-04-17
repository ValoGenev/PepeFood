package com.gegessen.controller;

import com.gegessen.GegessenApplication;
import com.gegessen.dto.category.FoodCategoryWithCountDto;
import com.gegessen.dto.restaurant.RestaurantAllPropertiesDto;
import com.gegessen.service.product.IProductService;
import io.imagekit.sdk.ImageKit;
import io.imagekit.sdk.config.Configuration;
import io.imagekit.sdk.utils.Utils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.ResponseEntity.ok;


@RestController
@RequestMapping("/config/api/v1")
public class MainController {

    private static final Logger LOGGER = getLogger(MainController.class);

    private final IProductService productService;

    @Autowired
    public MainController(IProductService productService) {
        this.productService = productService;
    }

    @GetMapping(value = "/categories", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<FoodCategoryWithCountDto>> getCategoriesWithValidProducts() {
        LOGGER.info("returning categories");
        return ok(productService.getCategoriesWithCount());
    }

    @GetMapping(value = "/imagekit/auth",produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String,String>> authImageKit() throws IOException {
        LOGGER.info("GEI IMAGEKIT");

        ImageKit imageKit=ImageKit.getInstance();
        Configuration config= Utils.getSystemConfig(GegessenApplication.class);
        imageKit.setConfig(config);

        Map<String,String> authenticationParams = imageKit.getAuthenticationParameters(null, Calendar.getInstance().getTimeInMillis() + 7200000L);

        return ResponseEntity.ok(authenticationParams);
    }
}
