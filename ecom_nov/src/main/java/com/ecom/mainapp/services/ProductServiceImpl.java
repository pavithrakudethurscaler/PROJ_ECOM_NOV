package com.ecom.mainapp.services;

import com.ecom.mainapp.exceptions.ProductNotFoundException;
import com.ecom.mainapp.models.Product;
import com.ecom.mainapp.repos.CategoryRespository;
import com.ecom.mainapp.repos.ProductRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Primary
public class ProductServiceImpl implements ProductService {
    ProductRepository productRepository;
    CategoryRespository categoryRespository;

    public ProductServiceImpl(ProductRepository productRepository, CategoryRespository categoryRespository) {
        this.productRepository = productRepository;
        this.categoryRespository = categoryRespository;
    }

    @Override
    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProductById(long id) throws ProductNotFoundException {
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            return product.get();
        } else {
            throw new ProductNotFoundException("Product not found");
        }
    }

    @Override
    public Product updateProduct(Product product) throws ProductNotFoundException {
        Optional<Product> productOptional = productRepository.findById(product.getId());
        if (productOptional.isPresent()) {
            Product p = productOptional.get();
            p.setName(product.getName());
            p.setDescription(product.getDescription());
            p.setPrice(product.getPrice());
            p.setCategory(product.getCategory());
            productRepository.save(p);
            return p;
        } else {
            throw new ProductNotFoundException("Product not found");
        }
    }

    @Override
    public boolean deleteProductById(long id) throws ProductNotFoundException {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return true;
        } else {
            throw new ProductNotFoundException("Product not found");
        }
    }

    @Override
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }
}
