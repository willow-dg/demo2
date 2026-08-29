package com.example.demo.exceptions;

public class CartEmptyFoundException extends RuntimeException {
    public CartEmptyFoundException(){
        super("Cart is empty");
    }
}
