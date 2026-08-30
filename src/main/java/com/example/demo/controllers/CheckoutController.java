package com.example.demo.controllers;

import com.example.demo.dtos.CheckoutRequest;
import com.example.demo.dtos.CheckoutResponse;
import com.example.demo.dtos.ErrorDto;
import com.example.demo.exceptions.CartEmptyFoundException;
import com.example.demo.exceptions.CartNotFoundException;
import com.example.demo.exceptions.PaymentException;
import com.example.demo.services.CheckoutService;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.net.Webhook;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/checkout")
public class CheckoutController {

    private final CheckoutService checkoutService;
    @Value("${stripe.webhookSecretKey}")
    private String webhookKey;

    @PostMapping
    public CheckoutResponse checkout(@Valid @RequestBody CheckoutRequest request) {
        return checkoutService.checkout(request);
    }

    @PostMapping("/webhook")
    public ResponseEntity<Void> handleWebhook(
            @RequestHeader("Stripe-Signature") String signature,
            @RequestBody String payload
    ) {
        try {
            var event = Webhook.constructEvent(payload, signature, webhookKey);
            System.out.println(event.getType());
            var stripeObject = event.getDataObjectDeserializer().getObject().orElse(null);

            switch (event.getType()) {
                case "payment_intent.succeeded"->{
                    //update order status (PAID)
                }
                case "payment_intent.failed"->{
                    //update order status (FAILE)
                }
            }
            return ResponseEntity.ok().build();

        } catch (SignatureVerificationException e) {
            return ResponseEntity.badRequest().build();
        }
    }


    @ExceptionHandler({CartNotFoundException.class, CartEmptyFoundException.class})
    public ResponseEntity<ErrorDto> handleException(Exception e) {
        return ResponseEntity.badRequest().body(new ErrorDto(e.getMessage()));
    }

    @ExceptionHandler({PaymentException.class})
    public ResponseEntity<?> handlePaymentException(PaymentException e) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorDto("Error creating a checkout session"));
    }

}
