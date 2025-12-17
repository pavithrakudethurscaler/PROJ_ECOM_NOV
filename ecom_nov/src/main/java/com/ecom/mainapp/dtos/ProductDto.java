package com.ecom.mainapp.dtos;

import com.ecom.mainapp.models.Category;
import com.ecom.mainapp.models.Product;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDto {
    private String name;
    private String description;
    private double price;
    private String category;

    public Product toProduct() {
        Product product = new Product();
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);

        Category category = new Category();
        category.setCategoryName(this.category);
        product.setCategory(category);
        return product;
    }
}
