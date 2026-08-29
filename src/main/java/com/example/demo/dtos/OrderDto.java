package com.example.demo.dtos;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderDto {
    private LocalDateTime  createdAt;
    private Long id;
    private String status;
    private List<OrderItemDto> items;
    private BigDecimal totalPrice;

}
