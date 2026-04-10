package com.example.demo;


import com.example.demo.entities.Category;
import com.example.demo.entities.Product;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Demo2Application {

    public static void main(String[] args) {

        var product = Product.builder()
                .name("product")
                .build();

        var category= new Category("category");

        product.setCategory(category);
        category.getProducts().add(product);

        System.out.println(category);

    }
}
