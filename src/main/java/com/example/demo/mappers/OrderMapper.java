package com.example.demo.mappers;

import com.example.demo.dtos.OrderDto;
import com.example.demo.entities.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "Spring")
public interface OrderMapper {
    @Mapping(target = "status",expression = "java(order.getStatus().name())")
    OrderDto toOrderDto(Order order);
}
