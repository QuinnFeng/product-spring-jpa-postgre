package com.bezkoder.spring.jpa.postgresql.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.bezkoder.spring.jpa.postgresql.model.Product;
import com.bezkoder.spring.jpa.postgresql.service.ProductService;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:8081")
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
    public String deleteProductById(@PathVariable int id) {
        productService.deleteProductById(id);
        return "Product deleted successfully!";
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
}
