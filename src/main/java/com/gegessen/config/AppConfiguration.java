package com.gegessen.config;

import com.gegessen.repository.*;
import com.gegessen.service.bucket.BucketService;
import com.gegessen.service.bucket.IBucketService;
import com.gegessen.service.email.CustomMailSender;
import com.gegessen.service.order.IOrderService;
import com.gegessen.service.order.OrderService;
import com.gegessen.service.product.IProductService;
import com.gegessen.service.product.ProductService;
import com.gegessen.service.restaurant.IRestaurantService;
import com.gegessen.service.restaurant.RestaurantService;
import com.gegessen.service.user.IUserService;
import com.gegessen.service.user.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Properties;

@Configuration
public class AppConfiguration {

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);

        mailSender.setUsername("trip.with.me.bg@gmail.com");
        mailSender.setPassword("Jonatan1999%");

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");

        return mailSender;
    }


    @Bean
    public CustomMailSender mailSender(){
        return new CustomMailSender(javaMailSender());
    }


    @Bean
    IProductService productService(IProductRepository productRepository,IRestaurantService restaurantService,ModelMapper modelMapper){
        return new ProductService(productRepository,restaurantService,modelMapper);
    }

    @Bean
    IOrderService orderService(IOrderRepository orderRepository,IUserService userService,IBucketService bucketService,ModelMapper modelMapper){
        return new OrderService(orderRepository,userService,bucketService,modelMapper);
    }

    @Bean
    IBucketService bucketService(IBucketRepository bucketRepository,IProductService productService,ModelMapper modelMapper){
        return new BucketService(bucketRepository,productService,modelMapper);
    }

    @Bean
    IUserService userService(ModelMapper modelMapper, IUserRepository userRepository,PasswordEncoder passwordEncoder) {
        return new UserService(userRepository,modelMapper,passwordEncoder);
    }

    @Bean
    IRestaurantService restaurantService(IRestaurantRepository restaurantRepository,IUserService userService,ModelMapper modelMapper){
        return new RestaurantService(restaurantRepository,userService,modelMapper);
    }

    @Bean
    ModelMapper modelMapper(){
        return new ModelMapper();
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
}
