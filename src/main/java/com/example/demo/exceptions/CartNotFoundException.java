package com.example.demo.exceptions;

public class CartNotFoundException extends RuntimeException {
    public CartNotFoundException() {
        super("Cart not found");
    }

}
