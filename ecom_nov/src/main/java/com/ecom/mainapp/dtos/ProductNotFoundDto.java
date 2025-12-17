package com.ecom.mainapp.dtos;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProductNotFoundDto {
    private int errorCode;
    private String errorMessage;
}
