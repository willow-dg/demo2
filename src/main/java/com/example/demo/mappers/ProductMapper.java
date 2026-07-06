package com.example.demo.mappers;

import com.example.demo.dtos.ProductDto;
import com.example.demo.entities.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(target = "categoryId",source = "category.id")
    ProductDto toDto(Product product);
    @Mapping(target = "category.id",source = "categoryId")
    Product toEntity(ProductDto productDto);
    @Mapping(target = "id",ignore = true)
    void updateEntity(ProductDto productDto, @MappingTarget Product product);
}
