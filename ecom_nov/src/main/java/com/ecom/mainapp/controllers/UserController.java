package com.ecom.mainapp.controllers;

import com.ecom.mainapp.dtos.LoginRequestDto;
import com.ecom.mainapp.dtos.SignUpRequestDto;
import com.ecom.mainapp.dtos.UserResponseDto;
import com.ecom.mainapp.models.Token;
import com.ecom.mainapp.models.User;
import com.ecom.mainapp.services.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    private UserService userService;

    UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public UserResponseDto signUp(@RequestBody SignUpRequestDto signUpRequestDto) {
        User user =  userService.signUp(signUpRequestDto.getName(),
                signUpRequestDto.getEmail(),
                signUpRequestDto.getPassword());
        return UserResponseDto.from(user);
    }

    @PostMapping("/login")
    public Token login(@RequestBody LoginRequestDto loginRequestDto) {
        return userService.login(loginRequestDto.getEmail(), loginRequestDto.getPassword());

    }

    @GetMapping("/validate/{token}")
    public UserResponseDto validateToken(@PathVariable String token) {
        User user = userService.validateToken(token);
        if(user == null) {
            return null;
        }
        return UserResponseDto.from(user);
    }


}


/*
 Order
 /create/order
 User id
 Payment Id
 Inventory Details
 */
