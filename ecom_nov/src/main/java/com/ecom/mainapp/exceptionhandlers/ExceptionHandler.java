package com.ecom.mainapp.exceptionhandlers;

import com.ecom.mainapp.dtos.ProductNotFoundDto;
import com.ecom.mainapp.exceptions.ProductNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(value = { ProductNotFoundException.class })
    public ResponseEntity<ProductNotFoundDto> handleProductNotFoundException(ProductNotFoundException exception) {
        ProductNotFoundDto productNotFoundDto = new ProductNotFoundDto();
        productNotFoundDto.setErrorCode(exception.getErrorCode());
        productNotFoundDto.setErrorMessage(exception.getMessage());
        return new ResponseEntity<>(productNotFoundDto, HttpStatus.NOT_FOUND);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(value = { NullPointerException.class })
    public ResponseEntity<String> handleNullPointerException(NullPointerException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
