package com.example.demo.controllers;

import com.example.demo.dtos.ProductDto;
import com.example.demo.entities.Product;
import com.example.demo.mappers.ProductMapper;
import com.example.demo.repositories.CategoryRepository;
import com.example.demo.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
@RestController
@RequestMapping("/products")
@AllArgsConstructor
public class ProductController {
    private final ProductMapper productMapper;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

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


    //创建产品
    @PostMapping
    public ResponseEntity<ProductDto> createProduct(
            @RequestBody ProductDto productDto,
            UriComponentsBuilder uriComponentsBuilder) {
        if (productDto == null) {
            return ResponseEntity.badRequest().build();
        }
        var product = productMapper.toEntity(productDto);
        productRepository.save(product);
        var productDto1 = productMapper.toDto(product);
        var uri = uriComponentsBuilder.path("/products/{id}")
                .buildAndExpand(productDto1.getId())
                .toUri();
        return ResponseEntity.created(uri).body(productDto1);
    }

    //更新资源
    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct(
            @PathVariable(name = "id") Long id,
            @RequestBody ProductDto productDto
    ){
        var category = categoryRepository.findById(productDto.getCategoryId()).orElse(null);
        if (category == null) {
            return ResponseEntity.notFound().build();
        }
        var product  = productRepository.findById(id).orElse(null);
        if (product == null) {
            return ResponseEntity.notFound().build();
        }
        productMapper.updateEntity(productDto,product);
        product.setCategory(category);
        productRepository.save(product);
        productDto.setId(product.getId());
        return ResponseEntity.ok(productDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable(name = "id") Long id) {
        if (!productRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        productRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
