package com.ecom.mainapp.models;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name="users")
public class User extends BaseModel {
    private String username;
    private String password;
    private String emailAddress;
    private UserType userType;
}
