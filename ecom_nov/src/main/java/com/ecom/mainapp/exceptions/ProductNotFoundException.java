package com.ecom.mainapp.exceptions;

public class ProductNotFoundException extends Exception {
    private String message;
    public ProductNotFoundException(String message) {
        super(message);
    }
}
