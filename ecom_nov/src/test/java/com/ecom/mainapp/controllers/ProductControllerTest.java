package com.ecom.mainapp.controllers;

import com.ecom.mainapp.dtos.ProductDto;
import com.ecom.mainapp.exceptions.ProductNotFoundException;
import com.ecom.mainapp.models.Product;
import com.ecom.mainapp.services.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class ProductControllerTest {

    @Autowired
    private ProductController productController;

    @MockitoBean
    private ProductService productService;

    @Test
    void testGetAllProducts() {
        // Arrange
        List<Product> mockProducts = new ArrayList<>();
        mockProducts.add(new Product());
        when(productService.getProducts()).thenReturn(mockProducts);

        // Act
        List<Product> response = productController.getAllProducts();

        // Assert
        assertEquals(1, response.size());
        verify(productService, times(1)).getProducts();
    }

    @Test
    void testGetProductById_Success() throws ProductNotFoundException {
        // Arrange
        Product mockProduct = new Product();
        mockProduct.setId(1);
        when(productService.getProductById(1)).thenReturn(mockProduct);

        // Act
        Product response = productController.getProductById(1);

        // Assert
        assertNotNull(response);
        assertEquals(1, response.getId());
    }

    @Test
    void testCreateProduct() {
        // Arrange
        ProductDto dto = new ProductDto(); // Assuming valid setters exist
        Product productFromDto = new Product();
        when(productService.createProduct(any(Product.class))).thenReturn(productFromDto);

        // Act
        ResponseEntity<Product> response = productController.createProduct(dto);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void testDeleteProduct_Found() throws ProductNotFoundException {
        // Arrange
        when(productService.deleteProductById(1)).thenReturn(true);

        // Act
        ResponseEntity<String> response = productController.deleteProduct(1);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Product deleted successfully", response.getBody());
    }

    @Test
    void testDeleteProduct_NotFound() throws ProductNotFoundException {
        // Arrange
        when(productService.deleteProductById(1)).thenReturn(false);

        // Act
        ResponseEntity<String> response = productController.deleteProduct(1);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Product not found", response.getBody());
    }

    @Test
    void testHandleProductNotFoundException() {
        // Arrange
        ProductNotFoundException exception = new ProductNotFoundException("Not found");

        // Act
        ResponseEntity<String> response = productController.handleProductNotFoundException(exception);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Not found", response.getBody());
    }
}