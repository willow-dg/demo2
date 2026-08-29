package com.example.demo.services;

import com.example.demo.dtos.OrderDto;
import com.example.demo.exceptions.OrderNotFoundException;
import com.example.demo.exceptions.ProductNotFoundException;
import com.example.demo.mappers.OrderMapper;
import com.example.demo.repositories.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class OrderService {

    private final UserService userService;
    private AuthService authService;
    private OrderRepository orderRepository;
    private OrderMapper orderMapper;

    public List<OrderDto> getOrders() {
        var orders = orderRepository.getOrdersByCustomer(authService.getCurrentUser());
        return orders.stream().map(orderMapper::toOrderDto).toList();
    }

    public OrderDto getOrder(Long orderId) {
        var order = orderRepository
                .getOrderWithItems(orderId)
                .orElseThrow(OrderNotFoundException::new);

        var user = authService.getCurrentUser();
        if (!order.isPlaceBy(user)) {
            throw new AccessDeniedException("You are not allowed to access this order.");
        }

        return orderMapper.toOrderDto(order);
    }


}
