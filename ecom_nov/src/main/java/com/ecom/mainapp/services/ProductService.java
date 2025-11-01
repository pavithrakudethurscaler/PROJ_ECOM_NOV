package com.ecom.mainapp.services;

import com.ecom.mainapp.exceptions.ProductNotFoundException;
import com.ecom.mainapp.models.Product;

import java.util.List;

public interface ProductService {
    List<Product> getProducts();
    Product getProductById(int id) throws ProductNotFoundException;
    Product updateProduct(Product product) throws ProductNotFoundException;
    boolean deleteProductById(int id) throws ProductNotFoundException;
    Product createProduct(Product product);
}
