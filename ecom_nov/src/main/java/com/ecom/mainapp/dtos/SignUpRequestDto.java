package com.ecom.mainapp.dtos;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SignUpRequestDto {
    private String email;
    private String name;
    private String password;
}
