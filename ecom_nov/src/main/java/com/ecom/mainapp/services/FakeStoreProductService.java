package com.ecom.mainapp.services;

import com.ecom.mainapp.dtos.FakeStoreProductDto;
import com.ecom.mainapp.exceptions.ProductNotFoundException;
import com.ecom.mainapp.models.Category;
import com.ecom.mainapp.models.Product;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class FakeStoreProductService implements ProductService{
    RestTemplate restTemplate;
    public FakeStoreProductService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Product> getProducts() {
        FakeStoreProductDto[] fakeStoreProductDto = restTemplate.getForObject("https://fakestoreapi.com/products",
                FakeStoreProductDto[].class);
        List<Product> products = new ArrayList<>();
        for (FakeStoreProductDto fakeStoreProductDto1 : fakeStoreProductDto) {
            products.add(convertFakeStoreProductDtoToProduct(fakeStoreProductDto1));
        }
        return products;
    }

    public Product getProductById(int id) throws ProductNotFoundException {
        FakeStoreProductDto fakeStoreProductDto = restTemplate.getForObject("https://fakestoreapi.com/products/"+id,
                FakeStoreProductDto.class);
        if (fakeStoreProductDto == null) {
            throw new ProductNotFoundException("Product not found");
        }
        return convertFakeStoreProductDtoToProduct(fakeStoreProductDto);
    }

    public boolean deleteProductById(int id) throws ProductNotFoundException {
        if (getProductById(id) == null) {
            throw new ProductNotFoundException("Product not found");
        } else {
            try {
                restTemplate.delete("https://fakestoreapi.com/products/" + id);
            } catch (Exception e) {
                return false;
            }
            return true;
        }
    }

    public Product updateProduct(Product product) throws ProductNotFoundException {
        FakeStoreProductDto updatedFakeStoreRequestDto = product.getFakeStoreProductDto();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<FakeStoreProductDto> requestEntity =
                new HttpEntity<>(updatedFakeStoreRequestDto, headers);

        ResponseEntity<FakeStoreProductDto> responseEntity = restTemplate.exchange(
                "https://fakestoreapi.com/products/" + product.getId(),
                HttpMethod.PUT,
                requestEntity,
                FakeStoreProductDto.class
        );
        if (responseEntity.getStatusCode().is2xxSuccessful() && responseEntity.getBody() != null) {
            return convertFakeStoreProductDtoToProduct(responseEntity.getBody());
        } else {
            throw new ProductNotFoundException("Product not found");
        }
    }

    public Product createProduct(Product product) {
        try {
            FakeStoreProductDto fakeStoreResponseDto = restTemplate.postForObject(
                    "https://fakestoreapi.com/products",
                    product.getFakeStoreProductDto(),
                    FakeStoreProductDto.class);
            assert fakeStoreResponseDto != null;
            return convertFakeStoreProductDtoToProduct(fakeStoreResponseDto);
        } catch (Exception ex) {
            return null;
        }
    }

    private Product convertFakeStoreProductDtoToProduct(FakeStoreProductDto fakeStoreProductDto) {
        Product product = new Product();
        product.setId(fakeStoreProductDto.getId());
        product.setName(fakeStoreProductDto.getTitle());
        product.setPrice(fakeStoreProductDto.getPrice());
        product.setDescription(fakeStoreProductDto.getDescription());
        Category category = new Category();
        category.setCategoryName(fakeStoreProductDto.getCategory());
        product.setCategory(category);
        return product;
    }
}
