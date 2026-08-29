package com.example.demo.controllers;

import com.example.demo.dtos.CheckoutRequest;
import com.example.demo.dtos.CheckoutResponse;
import com.example.demo.dtos.ErrorDto;
import com.example.demo.exceptions.CartEmptyFoundException;
import com.example.demo.exceptions.CartNotFoundException;
import com.example.demo.exceptions.PaymentException;
import com.example.demo.services.CheckoutService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/checkout")
public class CheckoutController {

    private final CheckoutService checkoutService;

    @PostMapping
    public CheckoutResponse checkout(@Valid @RequestBody CheckoutRequest request) {
        return checkoutService.checkout(request);
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
