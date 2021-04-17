package com.gegessen.service.product;

import com.gegessen.dto.category.FoodCategoryWithCountDto;
import com.gegessen.dto.order.OrderWithoutRelationDto;
import com.gegessen.dto.product.ProductAllPropertiesDto;
import com.gegessen.dto.product.ProductWithoutRelationDto;
import com.gegessen.dto.restaurant.RestaurantAllPropertiesDto;
import com.gegessen.entity.BucketEntity;
import com.gegessen.entity.OrderEntity;
import com.gegessen.entity.ProductEntity;
import com.gegessen.entity.RestaurantEntity;
import com.gegessen.exception.AlreadyExistingResourceException;
import com.gegessen.exception.ConflictException;
import com.gegessen.exception.ProductNotFoundException;
import com.gegessen.model.ProductCategory;
import com.gegessen.repository.IOrderRepository;
import com.gegessen.repository.IProductRepository;
import com.gegessen.service.order.IOrderService;
import com.gegessen.service.restaurant.IRestaurantService;
import com.gegessen.service.restaurant.RestaurantService;
import org.hibernate.service.spi.ServiceException;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import static com.gegessen.util.Constants.*;
import static java.lang.String.format;

public class ProductService implements IProductService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductService.class);
    private final IProductRepository productRepository;
    private final IRestaurantService restaurantService;
    private final ModelMapper modelMapper;

    @Autowired
    @Lazy
    private IOrderService orderService;


    @Autowired
    public ProductService(IProductRepository productRepository, IRestaurantService restaurantService, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.restaurantService = restaurantService;
        this.modelMapper = modelMapper;
    }

    @Override
    public Set<ProductWithoutRelationDto> findAll() {
        LOGGER.info(GET_ALL_PRODUCTS_MESSAGE);

        try {
            return productRepository
                    .findAll()
                    .stream()
                    .map(product -> modelMapper.map(product, ProductWithoutRelationDto.class))
                    .collect(Collectors.toSet());
        } catch (DataAccessException e) {
            LOGGER.error(DATABASE_ERROR_MESSAGE);
            throw new ServiceException(DATABASE_ERROR_MESSAGE);
        }
    }

    @Override
    public ProductAllPropertiesDto findOne(String id) {
        LOGGER.info(format(FIND_PRODUCT_BY_ID_MESSAGE, id));

        return modelMapper.map(findProduct(id), ProductAllPropertiesDto.class);
    }

    @Override
    @Transactional
    public void delete(String id) {
        LOGGER.info(format(DELETE_PRODUCT_BY_ID_MESSAGE, id));

        try {
            productRepository.findById(id).ifPresent((product)->{
               product
                       .getBuckets()
                       .stream()
                       .collect(Collectors.groupingBy(b->b.getOrder().getId()))
                       .values()
                       .stream()
                       .flatMap(List::stream)
                       .collect(Collectors.toSet())
                       .forEach(b->orderService.delete(b.getOrder().getId()));

               productRepository.delete(product);
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
    public ProductAllPropertiesDto create(ProductAllPropertiesDto product) {
        LOGGER.info(format(CREATE_PRODUCT_MESSAGE, product.getRestaurant().getId()));

        ProductEntity productToBeCreated = modelMapper.map(product, ProductEntity.class);

        productToBeCreated.setBuckets(new HashSet<>());

        RestaurantAllPropertiesDto restaurantInDb = restaurantService.findOne(product.getRestaurant().getId());

        productToBeCreated.setRestaurant(modelMapper.map(restaurantInDb,RestaurantEntity.class));

        return modelMapper.map(createProduct(productToBeCreated),ProductAllPropertiesDto.class);
    }



    @Override
    public ProductAllPropertiesDto update(ProductAllPropertiesDto product, String id) {
        LOGGER.info(format(UPDATE_PRODUCT_BY_ID_MESSAGE, product.getId()));

        ProductEntity productInDb = findProduct(id);

        ProductEntity productToBeUpdated = modelMapper.map(product,ProductEntity.class);

        productToBeUpdated.setId(productInDb.getId());

        productToBeUpdated.setRestaurant(productInDb.getRestaurant());

        productToBeUpdated.setBuckets(productInDb.getBuckets());

        return modelMapper.map(createProduct(productToBeUpdated),ProductAllPropertiesDto.class);
    }


    @Override
    public Set<OrderWithoutRelationDto> getProductOrders(String id) {

        return productRepository
                .findPlease(id)
                .stream()
                .map(o->modelMapper.map(o,OrderWithoutRelationDto.class))
                .collect(Collectors.toSet());
    }


    @Override
    public List<FoodCategoryWithCountDto> getCategoriesWithCount() {

        Comparator<FoodCategoryWithCountDto> comparator = (x,y) -> y.getCount() - x.getCount();

        List<FoodCategoryWithCountDto> availableCategories = new ArrayList<>();

        List<ProductEntity> products = productRepository.getAvailableProducts();

        Arrays.stream(ProductCategory.values()).forEach(c->{
            FoodCategoryWithCountDto obj = new FoodCategoryWithCountDto(c.getName(),c.getUrl(),0);


            int count = products.stream()
                    .filter(p->p.getCategory().name().equals(obj.getCategory()))
                    .mapToInt(ProductEntity::getQuantity)
                    .sum();

            System.out.println(obj.getCategory() + " " + count);

            obj.setCount(count);

            availableCategories.add(obj);
        });

        availableCategories.sort(comparator);


        return availableCategories;
    }


    private ProductEntity createProduct(ProductEntity product) {
        try {
            return productRepository.save(product);

        } catch (DataIntegrityViolationException e) {

            LOGGER.error(CONFLICT_CREATE_MESSAGE);
            throw new AlreadyExistingResourceException(EXISTING_RESOURCE_MESSAGE);
        } catch (DataAccessException e) {
            LOGGER.error(DATABASE_ERROR_MESSAGE, e);
            throw new ServiceException(DATABASE_ERROR_MESSAGE);
        }
    }


    private ProductEntity findProduct(String id) {
        try {
            return productRepository
                    .findById(id)
                    .orElseThrow(() -> new ProductNotFoundException(format(PRODUCT_NOT_FOUND_MESSAGE, id)));
        } catch (DataAccessException e) {
            LOGGER.error(DATABASE_ERROR_MESSAGE);
            throw new ServiceException(DATABASE_ERROR_MESSAGE);
        }
    }

}
