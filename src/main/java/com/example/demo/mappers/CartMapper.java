package com.example.demo.mappers;

import com.example.demo.dtos.CartDto;
import com.example.demo.dtos.CartItemDto;
import com.example.demo.entities.Cart;
import com.example.demo.entities.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CartMapper {
    @Mapping(target = "totalPrice",expression = "java(cart.getTotalPrice())")
    CartDto toDto(Cart cart);
    @Mapping(target = "totalPrice",expression = "java(cartItem.getTotalPrice())")
    CartItemDto toDto(CartItem cartItem);
}
