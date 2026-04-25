package com.example.demo.services;


import com.example.demo.entities.Category;
import com.example.demo.entities.Product;
import com.example.demo.repositories.CategoryRepository;
import com.example.demo.repositories.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@AllArgsConstructor
public class ProductService {
    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;

    @Transactional
//    public void persistRelated(){
//        var product=Product.builder()
//                .name("product 2")
//                .description("description")
//                .price(BigDecimal.valueOf(26.28))
//                .build();
//        var category=categoryRepository.findById((byte) 1).orElseThrow();
//        category.addProduct(product);
//        productRepository.save(product);
//    }
    public void persistRelated(){
        var product=Product.builder()
                .name("product 3")
                .description("description")
                .price(BigDecimal.valueOf(26.28))
                .build();
        var category=Category.builder()
                .name("category 3")
                        .build();
        category.addProduct(product);
        categoryRepository.save(category);
    }

    @Transactional
    public void removeRelated(){
        var category=categoryRepository.findById((byte) 1).orElseThrow();
        var product=category.getProducts().iterator().next();
        category.removeProduct(product);
        categoryRepository.save(category);
    }

    @Transactional
    public void updateProductPrice(){
        productRepository.updatePriceByCategory((byte) 1, BigDecimal.valueOf(1024.36));
    }

}
