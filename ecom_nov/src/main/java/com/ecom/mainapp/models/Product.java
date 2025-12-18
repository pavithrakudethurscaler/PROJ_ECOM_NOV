package com.ecom.mainapp.models;

import com.ecom.mainapp.dtos.FakeStoreProductDto;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Getter
@Setter
@Entity(name="products")
public class Product extends BaseModel {
    private String name;
    private String description;
    private double price;
    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    @Fetch(FetchMode.JOIN)
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
