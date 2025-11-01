package com.ecom.mainapp.models;

import com.ecom.mainapp.dtos.FakeStoreProductDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Product extends BaseModel {
    private String name;
    private String description;
    private double price;
    private Category category;

    public FakeStoreProductDto getFakeStoreProductDto() {
        FakeStoreProductDto fakeStoreProductDto = new FakeStoreProductDto();
        fakeStoreProductDto.setId(this.getId());
        fakeStoreProductDto.setCategory(this.category.getCategoryName());
        fakeStoreProductDto.setDescription(this.description);
        fakeStoreProductDto.setPrice(this.price);
        return fakeStoreProductDto;
    }
}
