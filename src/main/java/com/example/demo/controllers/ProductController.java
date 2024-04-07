package com.example.demo.controllers;

import com.example.demo.model.Product;
import com.example.demo.service.ProductService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * The type Product controller.
 */
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

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping("/create")
    public Product createProduct(@RequestBody Product product) {
    return productService.saveProduct(product);
  }

  @ResponseStatus(HttpStatus.OK)
  @PutMapping("/{id}")
    public Product updateProduct(@PathVariable Long id, @RequestBody Product product) {
    return productService.updateProduct(id, product);
  }

  @ResponseStatus(HttpStatus.OK)
  @DeleteMapping("/{id}")
    public Long deleteProduct(@PathVariable Long id) {
    productService.deleteProduct(id);
    return id;
  }

  @GetMapping("/owner/{ownerId}")
    public List<Product> getAllProductsByOwnerId(@PathVariable Long ownerId) {
    return productService.findAllProductsByOwnerId(ownerId);
  }

  @ResponseStatus(HttpStatus.OK)
  @PostMapping("/owner/{ownerId}/add/{productId}")
    public Product addProductToOwner(@PathVariable Long ownerId, @PathVariable Long productId) {
    return productService.addProductToOwner(ownerId, productId);
  }

  @ResponseStatus(HttpStatus.OK)
  @DeleteMapping("/owner/{ownerId}/remove/{productId}")
    public void removeProductFromOwner(@PathVariable Long ownerId, @PathVariable Long productId) {
    productService.removeProductFromOwner(ownerId, productId);
  }

  @ResponseStatus(HttpStatus.OK)
  @PutMapping("/owner/{ownerId}/update/{productId}")
    public Product updateProductForOwner(@PathVariable Long ownerId,
                                         @PathVariable Long productId,
                                         @RequestBody Product newProduct) {
    return productService.updateProductForOwner(ownerId, productId, newProduct);
  }

}
