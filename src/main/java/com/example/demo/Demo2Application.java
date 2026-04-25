package com.example.demo;


import com.example.demo.services.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.math.BigDecimal;

@SpringBootApplication
public class Demo2Application {

    public static void main(String[] args) {

        ApplicationContext context = SpringApplication.run(Demo2Application.class, args);
        var service = context.getBean(UserService.class);
        service.fetchPaginatedProducts(1, 3);

    }
}
