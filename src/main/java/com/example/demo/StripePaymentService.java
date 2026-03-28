package com.example.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

//@Component("Stripe")
public class StripePaymentService implements PaymentService {

    @Value("${stripe.apiUrl}")
    private String apiUrl;
    @Value("${stripe.enabled}")
    private boolean enabled;
    @Value("${stripe.timeout}")
    private int timeout;
    @Value("${stripe.supported-currencies}")
    private List<String> supportCurrencies;

    @Override
    public void processPayment(double amount) {
        System.out.println("Stripe API URL: " + apiUrl);
        System.out.println("Stripe enabled: " + enabled);
        System.out.println("Stripe timeout: " + timeout);
        System.out.println("Stripe supported-currencies: " + supportCurrencies);
        System.out.println("STRIPE");
        System.out.println("Amount:" + amount);
    }
}
