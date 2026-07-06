package com.example.demo.dtos;


import com.example.demo.entities.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.math.BigDecimal;
import java.math.BigInteger;

@AllArgsConstructor
@Data
public class ProductDto {

    private Long id;
    private String name;
    private BigDecimal price;
    private Byte categoryId;
    private String description;

}
