package com.ecom.mainapp.dtos;

import com.ecom.mainapp.models.Role;
import com.ecom.mainapp.models.User;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class UserResponseDto {
    private String name;
    private String email;
    private List<Role> roleList;

    public static UserResponseDto from(User user) {
        UserResponseDto responseDto = new UserResponseDto();
        responseDto.setEmail(user.getEmailAddress());
        responseDto.setName(user.getUsername());
        responseDto.setRoleList(user.getRoles());
        return responseDto;
    }
}
