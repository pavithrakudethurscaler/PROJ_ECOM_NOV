package com.ecom.mainapp.models;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name="ecom_categoy")
public class Category extends BaseModel{
    private String categoryName;
}
