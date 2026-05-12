package com.example.demo.dtos;


import com.example.demo.entities.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.math.BigInteger;

@AllArgsConstructor
@Getter
public class ProductDto {

    private Long id;
    private String name;
    private BigDecimal price;
    private Long categoryId;
}
