package com.example.demo.controllers;

import com.example.demo.dtos.ProductDto;
import com.example.demo.entities.Product;
import com.example.demo.mappers.ProductMapper;
import com.example.demo.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/products")
@AllArgsConstructor
public class ProductController {
    private final ProductMapper productMapper;
    private final ProductRepository productRepository;

    @GetMapping
    public List<ProductDto> findAll(
            @RequestParam(name = "categoryId",required = false) Byte categoryId,
            @RequestHeader(name = "headers",required = false) String headers
    ) {
        System.out.println(headers);
        List<Product> products;
        if (categoryId != null) {
            products = productRepository.findByCategoryId(categoryId);
        }else{
            products = productRepository.findAll();
        }
        return products.stream()
                .map(productMapper::toDto)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> findAllById(@PathVariable Long id) {
        var product = productRepository.findById(id).orElse(null);
        if (product == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(productMapper.toDto(product));
    }


}
