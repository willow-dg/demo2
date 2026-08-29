package com.example.demo.services;

import com.example.demo.dtos.CheckoutRequest;
import com.example.demo.dtos.CheckoutResponse;
import com.example.demo.entities.Order;
import com.example.demo.exceptions.CartEmptyFoundException;
import com.example.demo.exceptions.CartNotFoundException;
import com.example.demo.exceptions.PaymentException;
import com.example.demo.repositories.CartRepository;
import com.example.demo.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor/*只有被final字段声明的属性才会被并注入 */
public class CheckoutService {

    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final AuthService authService;
    private final CartService cartService;
    private final PaymentGateway paymentGateway;


    @Transactional
    public CheckoutResponse checkout(CheckoutRequest request) {

        var cart = cartRepository.getCartWithItems(request.getCartId()).orElse(null);
        if (cart == null) {
            throw new CartNotFoundException();
        }

        if (cart.isEmpty()) {
            throw new CartEmptyFoundException();
        }

        //手动映射
        var order = Order.fromCart(cart, authService.getCurrentUser());

        orderRepository.save(order);/**/

        try {
            var session = paymentGateway.createCheckoutSession(order);

            cartService.clearCart(cart.getId());

            return new CheckoutResponse(order.getId(), session.getCheckoutUrl());
        } catch (PaymentException ex) {
            orderRepository.delete(order);
            throw ex;
        }
    }
}
