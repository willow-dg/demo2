package com.example.demo;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class AppConfig {
    @Value("${payment-gateway}")
    private String paymentGateway;
    @Bean
    public PaymentService PayPal(){
        return new PayPalPaymentService();
    }
    @Bean("stripe")
    public PaymentService Stripe(){
        return new StripePaymentService();
    }
    @Bean
    public OrderService Order(){
        if (paymentGateway.equals("stripe")) {
            return new OrderService(Stripe());
        }
            return new OrderService(PayPal());
    }
}
