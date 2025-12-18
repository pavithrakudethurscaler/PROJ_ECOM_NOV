package com.ecom.mainapp.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity(name="users")
public class User extends BaseModel {
    private String username;
    private String password;
    private String emailAddress;
    private UserType userType;
    private String hashedPassword;
    @ManyToMany
    private List<Role> roles;
}
