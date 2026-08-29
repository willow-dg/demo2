package com.example.demo.services;

import com.example.demo.entities.Order;

public interface PaymentGateway {
    CheckoutSession createCheckoutSession(Order order);
}
