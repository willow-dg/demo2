package com.example.demo.mappers;

import com.example.demo.dtos.ProductDto;
import com.example.demo.entities.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(target = "categoryId",source = "category.id")
    ProductDto toDto(Product product);
}
