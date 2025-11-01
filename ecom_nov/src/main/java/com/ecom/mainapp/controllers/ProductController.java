package com.ecom.mainapp.controllers;

import com.ecom.mainapp.exceptions.ProductNotFoundException;
import com.ecom.mainapp.models.Product;
import com.ecom.mainapp.services.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    ProductService productService;
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/")
    public List<Product> getAllProducts() {
        List<Product> productList = productService.getProducts();
        return productList;
    }

    @GetMapping("/{id}")
    public Product getProductById(@PathVariable int id) throws ProductNotFoundException {
        return productService.getProductById(id);
    }

    @PostMapping("/")
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        ResponseEntity<Product> response = new ResponseEntity<>(productService.createProduct(product), HttpStatus.CREATED);
        return response;
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@RequestBody Product product) throws ProductNotFoundException {
        ResponseEntity<Product> response = new ResponseEntity<>(productService.updateProduct(product), HttpStatus.OK);
        return response;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable int id) throws ProductNotFoundException {
        boolean isDeleted = productService.deleteProductById(id);
        if (isDeleted) {
            return new ResponseEntity<>("Product deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
        }
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<String> handleProductNotFoundException(ProductNotFoundException e) {
        ResponseEntity<String> response = new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        return response;
    }
}
