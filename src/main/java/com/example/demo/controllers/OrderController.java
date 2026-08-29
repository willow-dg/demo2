package com.example.demo.controllers;

import com.example.demo.dtos.ErrorDto;
import com.example.demo.dtos.OrderDto;
import com.example.demo.exceptions.OrderNotFoundException;
import com.example.demo.mappers.OrderMapper;
import com.example.demo.repositories.OrderRepository;
import com.example.demo.services.AuthService;
import com.example.demo.services.OrderService;
import jakarta.websocket.server.PathParam;
import lombok.AllArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    @GetMapping
    public List<OrderDto> getAllOrders() {
        return orderService.getOrders();
    }

    @GetMapping("/{orderId}")
    public OrderDto getOrderById(@PathVariable(name = "orderId") Long orderId) {
        return orderService.getOrder(orderId);
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<Void> handleOrderNotFound() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorDto> handleAccessDenied(Exception ex) {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(new ErrorDto(ex.getMessage()));
    }

}
