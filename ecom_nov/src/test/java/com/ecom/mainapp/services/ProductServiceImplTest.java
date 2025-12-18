package com.ecom.mainapp.services;

import com.ecom.mainapp.exceptions.ProductNotFoundException;
import com.ecom.mainapp.models.Product;
import com.ecom.mainapp.repos.CategoryRespository;
import com.ecom.mainapp.repos.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class ProductServiceImplTest {

    @Autowired
    private ProductServiceImpl productService;

    @MockitoBean
    private ProductRepository productRepository;

    @MockitoBean
    private CategoryRespository categoryRespository;

    @Test
    void testGetProducts() {
        // Arrange
        List<Product> mockProducts = new ArrayList<>();
        mockProducts.add(new Product());
        when(productRepository.findAll()).thenReturn(mockProducts);

        // Act
        List<Product> result = productService.getProducts();

        // Assert
        assertEquals(1, result.size());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void testGetProductById_Success() throws ProductNotFoundException {
        // Arrange
        Product mockProduct = new Product();
        mockProduct.setId(10L);
        when(productRepository.findById(10L)).thenReturn(Optional.of(mockProduct));

        // Act
        Product result = productService.getProductById(10L);

        // Assert
        assertNotNull(result);
        assertEquals(10L, result.getId());
    }

    @Test
    void testGetProductById_NotFound() {
        // Arrange
        when(productRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ProductNotFoundException.class, () -> {
            productService.getProductById(1L);
        });
    }

    @Test
    void testUpdateProduct_Success() throws ProductNotFoundException {
        // Arrange
        Product existingProduct = new Product();
        existingProduct.setId(1L);
        existingProduct.setName("Old Name");

        Product updateDetails = new Product();
        updateDetails.setId(1L);
        updateDetails.setName("New Name");

        when(productRepository.findById(1L)).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(any(Product.class))).thenReturn(existingProduct);

        // Act
        Product updatedProduct = productService.updateProduct(updateDetails);

        // Assert
        assertEquals("New Name", updatedProduct.getName());
        verify(productRepository).save(any(Product.class));
    }

    @Test
    void testDeleteProductById_Success() throws ProductNotFoundException {
        // Arrange
        when(productRepository.existsById(1L)).thenReturn(true);

        // Act
        boolean result = productService.deleteProductById(1L);

        // Assert
        assertTrue(result);
        verify(productRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteProductById_NotFound() {
        // Arrange
        when(productRepository.existsById(1L)).thenReturn(false);

        // Act & Assert
        assertThrows(ProductNotFoundException.class, () -> {
            productService.deleteProductById(1L);
        });
    }

    @Test
    void testCreateProduct() {
        // Arrange
        Product product = new Product();
        when(productRepository.save(product)).thenReturn(product);

        // Act
        Product result = productService.createProduct(product);

        // Assert
        assertNotNull(result);
        verify(productRepository).save(product);
    }
}