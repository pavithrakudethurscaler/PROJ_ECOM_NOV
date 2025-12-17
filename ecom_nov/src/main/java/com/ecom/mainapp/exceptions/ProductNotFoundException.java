package com.ecom.mainapp.exceptions;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProductNotFoundException extends Exception {
    private final int errorCode = 101;
    private String message;
    public ProductNotFoundException(String message) {
        super(message);
    }
}
