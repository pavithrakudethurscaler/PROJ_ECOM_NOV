package com.ecom.mainapp.services;

import com.ecom.mainapp.dtos.FakeStoreProductDto;
import com.ecom.mainapp.exceptions.ProductNotFoundException;
import com.ecom.mainapp.models.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class FakeStoreProductServiceTest {

    @Autowired
    private FakeStoreProductService fakeStoreProductService;

    @MockitoBean
    private RestTemplate restTemplate;

    @Test
    void testGetProductById_Success() throws ProductNotFoundException {
        // Arrange
        FakeStoreProductDto mockDto = new FakeStoreProductDto();
        mockDto.setId(1L);
        mockDto.setTitle("Test Product");
        mockDto.setPrice(100.0);
        mockDto.setCategory("Electronics");

        when(restTemplate.getForObject(anyString(), eq(FakeStoreProductDto.class)))
                .thenReturn(mockDto);

        // Act
        Product product = fakeStoreProductService.getProductById(1L);

        // Assert
        assertNotNull(product);
        assertEquals(1L, product.getId());
        assertEquals("Test Product", product.getName());
        assertEquals("Electronics", product.getCategory().getCategoryName());
    }

    @Test
    void testGetProductById_ThrowsException() {
        // Arrange
        when(restTemplate.getForObject(anyString(), eq(FakeStoreProductDto.class)))
                .thenReturn(null);

        // Act & Assert
        assertThrows(ProductNotFoundException.class, () -> {
            fakeStoreProductService.getProductById(99L);
        });
    }

    @Test
    void testGetProducts_Success() {
        // Arrange
        FakeStoreProductDto dto1 = new FakeStoreProductDto();
        dto1.setId(1L);
        FakeStoreProductDto[] mockArray = { dto1 };

        when(restTemplate.getForObject(anyString(), eq(FakeStoreProductDto[].class)))
                .thenReturn(mockArray);

        // Act
        var products = fakeStoreProductService.getProducts();

        // Assert
        assertEquals(1, products.size());
        verify(restTemplate, times(1)).getForObject(anyString(), eq(FakeStoreProductDto[].class));
    }

    @Test
    void testDeleteProductById_Success() throws ProductNotFoundException {
        // Arrange
        FakeStoreProductDto mockDto = new FakeStoreProductDto();
        mockDto.setId(1L);

        // Mock the internal getProductById check
        when(restTemplate.getForObject(anyString(), eq(FakeStoreProductDto.class)))
                .thenReturn(mockDto);

        // Act
        boolean result = fakeStoreProductService.deleteProductById(1L);

        // Assert
        assertTrue(result);
        verify(restTemplate, times(1)).delete(anyString());
    }

    @Test
    void testUpdateProduct_Success() throws ProductNotFoundException {
        // Arrange
        Product inputProduct = new Product();
        inputProduct.setId(1L);
        inputProduct.setName("Updated Name");

        FakeStoreProductDto responseDto = new FakeStoreProductDto();
        responseDto.setId(1L);
        responseDto.setTitle("Updated Name");

        ResponseEntity<FakeStoreProductDto> responseEntity = new ResponseEntity<>(responseDto, HttpStatus.OK);

        when(restTemplate.exchange(
                anyString(),
                eq(HttpMethod.PUT),
                any(HttpEntity.class),
                eq(FakeStoreProductDto.class)
        )).thenReturn(responseEntity);

        // Act
        Product result = fakeStoreProductService.updateProduct(inputProduct);

        // Assert
        assertNotNull(result);
        assertEquals("Updated Name", result.getName());
    }
}