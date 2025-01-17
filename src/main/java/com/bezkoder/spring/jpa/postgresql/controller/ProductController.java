package com.bezkoder.spring.jpa.postgresql.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.persistence.EntityNotFoundException;

import com.bezkoder.spring.jpa.postgresql.model.Product;
import com.bezkoder.spring.jpa.postgresql.service.ProductService;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    // Save or update a product
    @PostMapping
    public Product saveOrUpdateProduct(@RequestBody Product product) {
        return productService.saveOrUpdateProduct(product);
    }

    // Retrieve a product by ID
    @GetMapping("/{id}")
    public Optional<Product> getProductById(@PathVariable int id) {
        return productService.getProductById(id);
    }

    // Retrieve all products
    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    // Delete a product by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProductById(@PathVariable int id) {
        try {
            productService.deleteProductById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    // Update stock of a product
    @PutMapping("/{id}/stock")
    public Product updateProductStock(@PathVariable int id, @RequestParam int newStock) {
        return productService.updateProductStock(id, newStock);
    }

    // Retrieve products by brand
    @GetMapping("/brand/{brand}")
    public List<Product> getProductsByBrand(@PathVariable String brand) {
        return productService.getProductsByBrand(brand);
    }
    
    @PostMapping("/bulk-insert")
    public ResponseEntity<List<Product>> insertProducts(@RequestBody List<Product> products) {
        List<Product> insertedProducts = productService.insertProducts(products);
        return new ResponseEntity<>(insertedProducts, HttpStatus.CREATED);
    }
}
