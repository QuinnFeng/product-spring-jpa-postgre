package com.bezkoder.spring.jpa.postgresql.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityNotFoundException;

import com.bezkoder.spring.jpa.postgresql.model.Product;
import com.bezkoder.spring.jpa.postgresql.repository.ProductRepository;



@Service
public class ProductService {
	
	@Autowired
	private  ProductRepository productRepository;
	// Create or Update Product
    public Product saveOrUpdateProduct(Product product) {
        return productRepository.save(product);
    }

    // Retrieve a Product by ID
    public Optional<Product> getProductById(int id) {
        return productRepository.findById(id);
    }

    // Retrieve all Products
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // Delete a Product by ID
    public void deleteProductById(int id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Product with ID " + id + " not found.");
        }
    }

    // Update stock of a Product
    public Product updateProductStock(int id, int newStock) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            product.setStock(newStock);
            return productRepository.save(product);
        }
        throw new RuntimeException("Product not found with ID: " + id);
    }

    // Retrieve Products by brand (additional method for filtering)
    public List<Product> getProductsByBrand(String brand) {
        return productRepository.findByBrand(brand);
    }
    
    public List<Product> insertProducts(List<Product> products) {
        // Save all products in a batch operation
        return productRepository.saveAll(products);
    }
}

