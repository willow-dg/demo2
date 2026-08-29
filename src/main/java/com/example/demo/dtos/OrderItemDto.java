package com.example.demo.dtos;

import com.example.demo.entities.Product;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderItemDto {
    private OrderProductDto product;
    private int quantity;
    private BigDecimal unitPrice;

}
