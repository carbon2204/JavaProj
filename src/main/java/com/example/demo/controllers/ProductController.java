package com.example.demo.controllers;

import com.example.demo.model.Product;
import com.example.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    @PostMapping
    public Product saveProduct(@RequestBody Product product) {
        return productService.saveProduct(product);
    }

    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable Long id, @RequestBody Product product) {
        return productService.updateProduct(id, product);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }

    @GetMapping("/owner/{ownerId}")
    public List<Product> getAllProductsByOwnerId(@PathVariable Long ownerId) {
        return productService.findAllProductsByOwnerId(ownerId);
    }


    @PostMapping("/owner/{ownerId}/add/{productId}")
    public Product addProductToOwner(@PathVariable Long ownerId, @PathVariable Long productId) {
        return productService.addProductToOwner(ownerId, productId);
    }

    @DeleteMapping("/owner/{ownerId}/remove/{productId}")
    public void removeProductFromOwner(@PathVariable Long ownerId, @PathVariable Long productId) {
        productService.removeProductFromOwner(ownerId, productId);
    }

    @PutMapping("/owner/{ownerId}/update/{productId}")
    public Product updateProductForOwner(@PathVariable Long ownerId, @PathVariable Long productId, @RequestBody Product newProduct) {
        return productService.updateProductForOwner(ownerId, productId, newProduct);
    }

}
